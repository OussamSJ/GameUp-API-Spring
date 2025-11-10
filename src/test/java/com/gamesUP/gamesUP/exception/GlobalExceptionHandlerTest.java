package com.gamesUP.gamesUP.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void testHandleEntityNotFound() {
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");

        ResponseEntity<?> response = handler.handleNotFound(ex, request);

        assertEquals(404, response.getStatusCodeValue());
        assertBody(response, "Entity not found", "Not Found", 404);
    }

    @Test
    void testHandleCustomNotFound() {
        EntityDontExistException ex = new EntityDontExistException("Custom not found");

        ResponseEntity<?> response = handler.handleCustomNotFound(ex, request);

        assertEquals(404, response.getStatusCodeValue());
        assertBody(response, "Custom not found", "Not Found", 404);
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");

        ResponseEntity<?> response = handler.handleBadRequest(ex, request);

        assertEquals(400, response.getStatusCodeValue());
        assertBody(response, "Invalid argument", "Bad Request", 400);
    }

    @Test
    void testHandleAccessDenied() {
        AccessDeniedException ex = new AccessDeniedException("Forbidden");

        ResponseEntity<?> response = handler.handleForbidden(ex, request);

        assertEquals(403, response.getStatusCodeValue());
        assertBody(response, "Access denied", "Forbidden", 403);
    }

    @Test
    void testHandleAuthenticationException() {
        AuthenticationException ex = mock(AuthenticationException.class);
        when(ex.getMessage()).thenReturn("Unauthenticated");

        ResponseEntity<?> response = handler.handleUnauthorized(ex, request);

        assertEquals(401, response.getStatusCodeValue());
        assertBody(response, "Unauthorized", "Unauthorized", 401);
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new RuntimeException("Unexpected error");

        ResponseEntity<?> response = handler.handleAll(ex, request);

        assertEquals(500, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Unexpected error"));
    }

    private void assertBody(ResponseEntity<?> response, String expectedMessage, String expectedError, int expectedStatus) {
        assertTrue(response.getBody() instanceof Map);
        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertEquals(expectedMessage, body.get("message"));
        assertEquals(expectedError, body.get("error"));
        assertEquals(expectedStatus, body.get("status"));
        assertEquals("/api/test", body.get("path"));
        assertNotNull(body.get("timestamp"));
    }
}

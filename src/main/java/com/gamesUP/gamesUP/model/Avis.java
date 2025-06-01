package com.gamesUP.gamesUP.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 2000)
	private String commentaire;

	@Column(nullable = false)
	@Min(value = 1, message = "La note est entre 1 et 5" )
	@Max(value = 5, message = "La note est entre 1 et 5" )
	@NotNull
	private Integer note;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "game_id", nullable = false)
	@JsonBackReference
	private Game game;

}

package com.gamesUP.gamesUP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "Le nom de l'inventaire est obligatoire")
	private String name;

	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<InventoryLine> stock = new HashSet<>();
	
}

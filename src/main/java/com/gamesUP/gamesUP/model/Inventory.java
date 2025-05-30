package com.gamesUP.gamesUP.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;



@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<InventoryLine> stock = new HashSet<>();
	
}

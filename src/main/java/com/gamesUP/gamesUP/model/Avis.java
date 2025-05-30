package com.gamesUP.gamesUP.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "game_id", nullable = false)
	private Game game;

	@Column(length = 2000)
	private String commentaire;

	@Column(nullable = false)
	private int note;

}

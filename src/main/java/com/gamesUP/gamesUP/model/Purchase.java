package com.gamesUP.gamesUP.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
	private List<PurchaseLine> lines;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Enumerated(EnumType.STRING)
	private PurchaseStatus status;
}

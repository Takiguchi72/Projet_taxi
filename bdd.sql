set schema 'Projet_taxi';

create table tarif (
	departement int,
	prise_en_charge numeric(5,2),
	km_jour_semaine numeric(5,2),
	km_nuit_semaine numeric(5,2),
	km_jour_weekend numeric(5,2),
	km_nuit_weekend numeric(5,2),
	constraint pk_tarif primary key (departement)
);

insert into tarif values
(21,2.0,0.86,1.72,1.38,2.4),
(39,2.1,0.83,1.66,1.3,2.24),
(44,2.2,0.8,1.6,1.26,1.9),
(72,2.15,0.79,1.58,1.12,1.5),
(90,2.2,0.92,1.84,1.39,2.46);

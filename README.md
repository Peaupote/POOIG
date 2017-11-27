# POOIG

# TODO
- [x] terminer jeu de l'oie classique avec les deux variantes de fin
- [x] version pions ne peuvent pas cohabiter
- [x] cases prisons, case auberges
- [ ] numeri

## Architecture du dépot
```
├── README.md
└── src
    └── fr
        └── ip
            ├── Main.java
            └── model
                ├── core
                │   ├── ActionEvent.java
                │   ├── Board.java
                │   ├── Cell.java
                │   ├── Event.java
                │   ├── EventListener.java
                │   ├── Game.java
                │   ├── Pawn.java
                │   └── Player.java
                ├── goose
                │   ├── GooseCell.java
                │   ├── GooseGame.java
                │   └── GoosePlayer.java
                └── numeri
                    ├── NumeriGame.java
                    └── NumeriPlayer.java

```

On trouve dans le package core le code factorisé entre le jeu de l'oie et le numeri dont les spécifitées sont précisées dans leurs packages respectifs.

## Action event
Le modèle se base sur une logique d'événement (début d'un tour, fin d'un tour, un pion arrive sur une case...) qui sont représentés à travers l'interface fonctionelle ActionEvent.
La méthode à implémenter s'appelle run et prend en paramètre un événement qui a un nom.

## Les événements
Nous avons décidé de ne pas reprendre la classe native Event de java afin de s'éviter trop de code lourd et inutile pour ce projet.
Grossièrement on manipule un événement avec son nom et on peu décider de cesser la "propagation" des événements après l'éxécution de l'un d'entre eux.

## EventListener
L'interface EventListener représente les classes qui recoivent les événements comme le joueur ou la cellule. On peut donc déclencher les événements en connaissant leur nom ou en ajouter. 

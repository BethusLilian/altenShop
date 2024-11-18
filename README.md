# Alten Shop - backend
## Environnement
- JDK 17
- Springboot 3.3.5
- Base de données H2 intégrée
## Commandes pour lancer le microservice
Compilation :
```bash 
mvn clean install
```
Execution : 
```bash
mvn spring-boot:run
```
Tests :
```bash
mvn test
```
## Tests Postman
Vous trouverez la collection postman à la racine du projet. Elle contient les scripts suivants :
 - POST : ajout d'un produit
 - GET : Obtenir la liste complète de produit
 - GET : Obtenir un produit
 - PATCH : modifier un produit
 - DELETE : Supprimer un produit
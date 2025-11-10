# 🎮 GamesUP API – Spring Boot

## 📖 Description du projet
GamesUP est une plateforme de **vente de jeux de société en ligne**.  
Ce projet vise à **refondre entièrement l’API backend** pour la rendre plus **robuste, modulaire et sécurisée**, tout en intégrant un système de **recommandation intelligent** basé sur le Machine Learning.

Le backend est développé en **Java Spring Boot** et communique avec une **API Python FastAPI** dédiée aux recommandations.

---

## 🏗️ Objectifs du projet
- Reprendre et améliorer le code existant d’une première version non fonctionnelle.
- Mettre en place une **architecture RESTful** respectant les **principes SOLID**.
- Implémenter la **sécurité** via **Spring Security**.
- Intégrer **Hibernate/JPA** pour la gestion de la persistance.
- Ajouter un **système de recommandation** via une API Python (modèle KNN).
- Écrire des **tests unitaires et d’intégration** pour assurer la fiabilité de l’application.
- Documenter l’architecture et les choix techniques.

---

## ⚙️ Fonctionnalités principales
- Gestion des entités :
  - 👤 **Clients**
  - 🎲 **Jeux**
  - 🏢 **Éditeurs**
  - ✍️ **Auteurs**
  - 🛒 **Commandes**
- Système de **rôles** :
  - **Client** : création de compte, consultation et achat de jeux.
  - **Administrateur** : gestion complète des entités et des utilisateurs.
- 🔍 **Recherche** de jeux par nom, éditeur ou catégorie.
- 🔐 **Sécurité** basée sur JWT avec Spring Security.
- 🤖 **Recommandations personnalisées** via communication avec l’API Python (FastAPI + modèle KNN).

---

## 🧩 Architecture technique
- **Backend :** Spring Boot 3, Java 17  
- **ORM :** Hibernate / JPA  
- **Sécurité :** Spring Security + JWT  
- **Base de données :** PostgreSQL  
- **Tests :** JUnit 5, Mockito  
- **Recommandations :** API Python (FastAPI + KNN)

---

## 🧠 Système de recommandation
Une API Python indépendante reçoit les données d’achat des utilisateurs, entraîne un **modèle KNN** et renvoie des **suggestions de jeux similaires** à l’API Spring.  
La communication se fait via des **requêtes REST** entre les deux services.

---

## 🧪 Tests
Des **tests unitaires et d’intégration** ont été écrits pour :
- Vérifier le bon fonctionnement des **services** et **contrôleurs**.
- Garantir la **non-régression** des principales fonctionnalités.
- Fournir des **rapports de couverture de tests**.

---

## 🧾 Documentation
Le projet est accompagné de :
- Diagramme d’architecture  
- Diagramme de classes  
- Diagramme de composants  
- Diagramme de séquence  
- Rapport sur les principes SOLID appliqués  
- Analyse du système de recommandation

---

## 🚀 Lancer le projet localement
### Prérequis
- Java 17+
- Maven
- PostgreSQL
- (Optionnel) Python 3 + FastAPI

### Étapes
```bash
# Cloner le projet
git clone https://github.com/OussamSJ/GameUp-API-Spring.git
cd GameUp-API-Spring

# Construire et lancer le backend
mvn clean install
mvn spring-boot:run

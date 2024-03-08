<template>
  <div class="evenements">
    <h1>Liste des Événements</h1>
    <table>
      <thead>
        <tr>
          <th>Nom</th>
          <th>Date et Heure</th>
          <th>Durée</th>
          <th>Lieu</th>
          <th>Participants Max</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="evenement in evenements" :key="evenement.id">
          <td>{{ evenement.nom }}</td>
          <td>{{ evenement.date_heure }}</td>
          <td>{{ evenement.duree }}</td>
          <td>{{ evenement.lieu.nom }}</td>
          <td>{{ evenement.nombreMaxParticipants }}</td>
          <td>
            <button @click="modifierEvenement(evenement)">Modifier</button>
            <button class="rouge" @click="supprimerEvenement(evenement.id)">
              Supprimer
            </button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-if="evenementSelectionne">
      <h2>Mettre à jour l'événement</h2>
      <form @submit.prevent="mettreAJourEvenement">
        <div>
          <label for="nom">Nom :</label>
          <input id="nom" v-model="evenementSelectionne.nom" required />
        </div>
        <div>
          <label for="dateHeure">Date et Heure :</label>
          <input
            type="datetime-local"
            v-model="evenementSelectionne.date_heure"
            required
          />
        </div>
        <div>
          <label for="duree">Durée :</label>
          <input type="number" v-model="evenementSelectionne.duree" required />
        </div>
        <div>
          <label for="lieu">Lieu :</label>
          <select v-model="evenementSelectionne.lieu.id">
            <option v-for="lieu in lieux" :key="lieu.id" :value="lieu.id">
              {{ lieu.nom }}
            </option>
          </select>
        </div>
        <div>
          <label for="participantsMax">Participants Max :</label>
          <input
            type="number"
            v-model="evenementSelectionne.nombreMaxParticipants"
            required
          />
        </div>
        <button type="submit">Mettre à jour</button>
        <button @click="evenementSelectionne = null" type="button">
          Annuler
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      evenements: [],
      evenementSelectionne: null,
      lieux: [], // Assurez-vous de charger les lieux depuis votre API ou autre source
      url: "/api/v1/evenement/getAllEvenement",
    };
  },

  mounted() {
    this.chargerEvenements();
    this.chargerLieux(); // Assurez-vous d'implémenter cette méthode pour charger les lieux
  },

  methods: {
    chargerEvenements() {
      axios
        .get(this.url)
        .then((response) => {
          this.evenements = response.data;
        })
        .catch((error) =>
          console.error("Erreur lors du chargement des événements", error)
        );
    },

    chargerLieux() {
      axios
        .get(this.url + "/lister")
        .then((response) => {
          console.log(
            "Afficher les événements pour le membre ID:",
            response.data
          );

          this.lieux = response.data;
        })
        .catch((error) => {
          console.error("Erreur lors du chargement des lieux", error);
        });
    },

    modifierEvenement(evenement) {
      this.evenementSelectionne = { ...evenement };
    },

    mettreAJourEvenement() {
      axios
        .put(
          `/api/v1/evenement/updateEvenement/${this.evenementSelectionne.id}`,
          this.evenementSelectionne
        )
        .then(() => {
          this.chargerEvenements();
          this.evenementSelectionne = null;
        })
        .catch((error) =>
          console.error("Erreur lors de la mise à jour de l'événement", error)
        );
    },

    supprimerEvenement(id) {
      axios
        .delete(`/api/v1/evenement/deleteEvenement/${id}`)
        .then(() => this.chargerEvenements())
        .catch((error) =>
          console.error("Erreur lors de la suppression de l'événement", error)
        );
    },
  },
};
</script>

<style>
/* Style de base pour le conteneur principal */
.membres,
.evenements {
  font-family: Arial, sans-serif;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1,
h2 {
  color: #333;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th,
td {
  text-align: left;
  padding: 8px;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f2f2f2;
}

button {
  cursor: pointer;
  padding: 5px 10px;
  margin-right: 5px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

button.rouge {
  background-color: #dc3545;
}

button.rouge:hover {
  background-color: #c82333;
}

/* Style pour le formulaire */
form {
  margin-top: 20px;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
}

form label {
  display: block;
  margin-bottom: 5px;
}

form input,
form select {
  width: 100%;
  padding: 8px;
  margin-bottom: 20px;
  border-radius: 4px;
  border: 1px solid #ccc;
}

form button {
  width: auto;
  padding: 10px 15px;
  background-color: #28a745;
}

form button:hover {
  background-color: #218838;
}

/* Annuler bouton style */
button[type="button"] {
  background-color: #6c757d;
}

button[type="button"]:hover {
  background-color: #5a6268;
}
</style>

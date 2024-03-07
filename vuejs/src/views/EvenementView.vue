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
          <button @click="supprimerEvenement(evenement.id)">Supprimer</button>
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
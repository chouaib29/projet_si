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
        <td>{{ evenement.lieu.id }}</td>
        <td>{{ evenement.nombreMaxParticipants }}</td>
        <td>
          <button @click="modifierEvenement(evenement)">Modifier</button>
          <button @click="supprimerEvenement(evenement.id)">Supprimer</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      evenements: [],
      url: "/api/v1/evenement/getAllEvenement", // Utilisation du préfixe "/api" configuré dans vue.config.js
    };
  },
  mounted() {
    this.chargerEvenements();
  },
  methods: {
    chargerEvenements() {
      axios
          .get(this.url)
          .then((response) => {
            console.log("Réponse de l'API:", response.data);
            this.evenements = response.data;
          })
          .catch((error) =>
              console.error(
                  "Erreur lors du chargement des événements. Statut de la réponse :",
                  error.response.status
              )
          );
    },

    modifierEvenement(evenement) {
      // Logique pour modifier un événement, peut-être via un formulaire ou une redirection vers un composant de modification
      console.log("Modifier l'événement:", evenement.id);
    },
    supprimerEvenement(id) {
      axios
          .delete(`/api/v1/evenement/${id}`) // Utilisation du préfixe "/api" configuré dans vue.config.js
          .then(() => this.chargerEvenements())
          .catch((error) =>
              console.error("Erreur lors de la suppression de l'événement", error)
          );
    },
  },
};
</script>

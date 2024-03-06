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
          <td>{{ evenement.dateHeure }}</td>
          <td>{{ evenement.duree }}</td>
          <td>{{ evenement.lieu }}</td>
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
    };
  },
  mounted() {
    this.chargerEvenements();
  },
  methods: {
    chargerEvenements() {
      axios
        .get("http://localhost:8082/api/v1/evenement/getAllEvenement")
        .then((response) => {
          this.evenements = response.data;
        })
        .catch((error) =>
          console.error("Erreur lors du chargement des événements", error)
        );
    },
    modifierEvenement(evenement) {
      // Logique pour modifier un événement, peut-être via un formulaire ou une redirection vers un composant de modification
      console.log("Modifier l'événement:", evenement.id);
    },
    supprimerEvenement(id) {
      axios
        .delete(`http://localhost:8082/api/v1/evenement/${id}`)
        .then(() => this.chargerEvenements())
        .catch((error) =>
          console.error("Erreur lors de la suppression de l'événement", error)
        );
    },
  },
};
</script>

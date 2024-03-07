<template>
  <div class="membres">
    <h1>Liste des membres</h1>
    <table>
      <thead>
      <tr>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Adresse</th>
        <th>Événements Inscrits</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="membre in membres" :key="membre.id">
        <td>{{ membre.nom }}</td>
        <td>{{ membre.prenom }}</td>
        <td>{{ membre.adresse }}</td>
        <td>
          <button @click="voirEvenements(membre.id)">Voir Événements</button>
        </td>
        <td>
          <button @click="supprimerMembre(membre.id)">Supprimer</button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- Table pour afficher les événements -->
    <div v-if="evenementsDuMembre.length > 0">
      <h2>Événements du membre</h2>
      <table>
        <thead>
        <tr>
          <th>Nom de l'événement</th>
          <th>Date et heure</th>
          <!-- Ajoutez d'autres colonnes si nécessaire -->
        </tr>
        </thead>
        <tbody>
        <tr v-for="evenement in evenementsDuMembre" :key="evenement.id">
          <td>{{ evenement.nom }}</td>
          <td>{{ evenement.date_heure }}</td>
          <!-- Ajoutez d'autres cellules si nécessaire -->
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else-if="evenementsCharge && evenementsDuMembre.length === 0">
      <p>Aucun événement pour ce membre.</p>
    </div>
    <div v-else-if="!evenementsCharge">
      <p>Chargement des événements...</p>
    </div>
    <div v-else>
      <p>Aucun événement pour ce membre.</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      membres: [],
      evenementsDuMembre: [],
      evenementsCharge: false,
    };
  },
  methods: {
    chargerMembres() {
      axios
          .get("/api/v1/membre/lister")
          .then((response) => {
            this.membres = response.data;
          })
          .catch((error) =>
              console.error("Erreur lors du chargement des membres", error)
          );
    },
    voirEvenements(idMembre) {
      this.evenementsCharge = true; // Indique que les événements sont en cours de chargement
      axios
          .get(`/api/v1/membre/${idMembre}/evenements`)
          .then((response) => {
            this.evenementsDuMembre = response.data;
          })
          .catch((error) => {
            console.error(
                "Erreur lors du chargement des événements du membre",
                error
            );
          })
          .finally(() => {
            this.evenementsCharge = false; // Fin du chargement des événements
          });
    },
    supprimerMembre(id) {
      if (id) {
        axios
            .delete(`/api/v1/membre/${id}`)
            .then(() => this.chargerMembres())
            .catch((error) =>
                console.error("Erreur lors de la suppression", error)
            );
      } else {
        console.error("ID du membre non défini lors de la suppression");
      }
    },
  },
  mounted() {
    this.chargerMembres();
  },
};
</script>

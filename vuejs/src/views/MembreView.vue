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
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      membres: [],
    };
  },
  mounted() {
    this.chargerMembres();
  },
  methods: {
    chargerMembres() {
      axios
          .get("/api/v1/membre/lister") // Utilisation du préfixe "/api" configuré dans vue.config.js
          .then((response) => {
            console.log("Afficher les événements pour le membre ID:", response.data);
            this.membres = response.data;
          })
          .catch((error) =>
              console.error("Erreur lors du chargement des membres", error)
          );
    },
    voirEvenements(idMembre) {
      console.log("Afficher les événements pour le membre ID:", idMembre);

      // Appel au service pour récupérer les événements du membre
      axios
          .get(`/api/v1/membre/${idMembre}/evenements`)
          .then((response) => {
            this.evenementsDuMembre = response.data;
            console.log("Événements du membre:", this.evenementsDuMembre);
          })
          .catch((error) => {
            console.error("Erreur lors du chargement des événements du membre", error);
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
};
</script>

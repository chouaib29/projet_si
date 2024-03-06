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
        .get("http://localhost:8082/api/v1/membre/lister")
        .then((response) => {
          this.membres = response.data;
        })
        .catch((error) =>
          console.error("Erreur lors du chargement des membres", error)
        );
    },
    voirEvenements(idMembre) {
      console.log("Afficher les événements pour le membre ID:", idMembre);
      // Implémenter la logique pour afficher les événements d'un membre
    },
    supprimerMembre(id) {
      axios
        .delete(`http://localhost:8080/api/v1/membres/${id}`)
        .then(() => this.chargerMembres())
        .catch((error) =>
          console.error("Erreur lors de la suppression", error)
        );
    },
  },
};
</script>
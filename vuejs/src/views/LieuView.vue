<template>
  <div class="lieux">
    <h1>Liste des Lieux</h1>
    <table>
      <thead>
        <tr>
          <th>Nom</th>
          <th>Adresse</th>
          <th>Capacité d'accueil</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="lieu in lieux" :key="lieu.id">
          <td>{{ lieu.nom }}</td>
          <td>{{ lieu.adresse }}</td>
          <td>{{ lieu.capaciteAccueil }}</td>
          <td>
            <button @click="modifierLieu(lieu)">Modifier</button>
            <button @click="supprimerLieu(lieu.id)">Supprimer</button>
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
      lieux: [],
    };
  },
  mounted() {
    this.chargerLieux();
  },
  methods: {
    chargerLieux() {
      axios
        .get("http://localhost:8082/api/v1/lieux")
        .then((response) => {
          this.lieux = response.data;
        })
        .catch((error) =>
          console.error("Erreur lors du chargement des lieux", error)
        );
    },
    supprimerLieu(id) {
      axios
        .delete(`http://localhost:8080/api/v1/lieux/${id}`)
        .then(() => this.chargerLieux())
        .catch((error) =>
          console.error("Erreur lors de la suppression du lieu", error)
        );
    },
  },
};
</script>

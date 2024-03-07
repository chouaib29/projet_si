<template>
  <div class="lieux">
    <h1>Liste des Lieux</h1>
    <p id="statusLieu"></p>
    <p id="messageLieu"></p>
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
            <button @click="voirEvenements(lieu.id)">Voir Événements</button>
          </td>
          <td>
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
      url: "/api/v1/lieux", // Utilisation du préfixe "/api" configuré dans vue.config.js
    };
  },
  methods: {
    chargerLieux() {
      axios
        .get(this.url +"/lister")
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
    supprimerLieu(id) {
      axios
        .delete(`${this.url}/${id}`)
        .then((response) => {
          this.setStatusEtMessage(response, "Lieu");
          this.chargerLieux();
        })
        .catch((error) => {
          console.error("Erreur lors de la suppression du lieu", error);
        });
    },
    setStatusEtMessage(response, type) {
      document.getElementById(`status${type}`).innerHTML =
        "Status : " +
        (response.status >= 200 && response.status < 300 ? "OK" : "KO");
      document.getElementById(`message${type}`).innerHTML =
        "Message : " + response.status;
    },
  },
  mounted() {
    this.chargerLieux();
  },
};
</script>

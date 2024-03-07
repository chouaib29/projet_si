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
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="lieu in lieux" :key="lieu.id">
        <td>{{ lieu.nom }}</td>
        <td>{{ lieu.adresse }}</td>
        <td>{{ lieu.capaciteAccueil }}</td>
        <td>
          <button @click="supprimerLieu(lieu.id)">Supprimer</button>
        </td>
      </tr>
      </tbody>
    </table>
    <!-- Bouton pour afficher le formulaire d'ajout -->
    <button @click="afficherFormulaire = !afficherFormulaire">
      Ajouter Lieu
    </button>

    <!-- Formulaire d'ajout d'un lieu -->
    <div v-if="afficherFormulaire" class="formulaire-ajout">
      <h2>Ajouter un nouveau lieu</h2>
      <form @submit.prevent="ajouterLieu">
        <div>
          <label for="nom">Nom :</label>
          <input id="nom" v-model="nouveauLieu.nom" required />
        </div>
        <div>
          <label for="adresse">Adresse :</label>
          <input id="adresse" v-model="nouveauLieu.adresse" required />
        </div>
        <div>
          <label for="capaciteAccueil">Capacité d'accueil :</label>
          <input
              id="capaciteAccueil"
              type="number"
              v-model.number="nouveauLieu.capaciteAccueil"
              required
          />
        </div>
        <button type="submit">Enregistrer</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      lieux: [],
      afficherFormulaire: false,
      nouveauLieu: {
        nom: "",
        adresse: "",
        capaciteAccueil: null,
      },
      url: "/api/v1/lieux", // Utilisation du préfixe "/api" configuré dans vue.config.js
    };
  },
  methods: {
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
    ajouterLieu() {
      axios
          .post(this.url, this.nouveauLieu)
          .then((response) => {
            console.log("Réponse de l'API:", response.data);
            this.chargerLieux(); // Recharge la liste après l'ajout
            this.nouveauLieu = { nom: "", adresse: "", capaciteAccueil: null }; // Réinitialise le formulaire
            this.afficherFormulaire = false; // Cache le formulaire
          })
          .catch((error) => {
            console.error("Erreur lors de l'ajout d'un nouveau lieu", error);
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

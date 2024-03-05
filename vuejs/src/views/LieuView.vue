<template>
  <div class="lieux">
    <h1>Liste des lieux</h1>
    <p id="status"></p>
    <p id="message"></p>
    <table>
      <thead>
        <tr>
          <th>IdLieu</th>
          <th>Nom</th>
          <th>Adresse</th>
          <th>Capacité d'accueil</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="lieu in lieux" :key="lieu.idLieu">
          <td>{{ lieu.idLieu }}</td>
          <td>{{ lieu.nom }}</td>
          <td>{{ lieu.adresse }}</td>
          <td>{{ lieu.capacite }}</td>
          <td>
            <button v-if="isLoggedInAdmin" @click="editLieu(lieu)">Modifier</button>
            <button v-if="isLoggedInAdmin" @click="deleteLieu(lieu.idLieu)">Supprimer</button>
            <button @click="lieuVu = lieu">Voir plus</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-if="isLoggedInAdmin">
      <h2>Ajouter un lieu</h2>
      <form @submit.prevent="addLieu">
        <label>Nom :</label>
        <input type="text" v-model="nouveauLieu.nom" required />
        <br />
        <label>Adresse :</label>
        <input type="text" v-model="nouveauLieu.adresse" required />
        <br />
        <label>Capacité d'accueil :</label>
        <input type="number" v-model="nouveauLieu.capacite" required />
        <br />
        <button type="submit">Ajouter</button>
      </form>
    </div>
    <div v-if="lieuSelectionne">
      <h2>Modifier le lieu</h2>
      <form @submit.prevent="updateLieu">
        <label>Nom :</label>
        <input type="text" v-model="lieuSelectionne.nom" required />
        <br />
        <label>Adresse :</label>
        <input type="text" v-model="lieuSelectionne.adresse" required />
        <br />
        <label>Capacité d'accueil :</label>
        <input type="number" v-model="lieuSelectionne.capacite" required />
        <br />
        <button type="submit">Modifier</button>
        <button @click="lieuSelectionne = null">Annuler</button>
      </form>
    </div>
    <VoirElement v-if="lieuVu" :key="lieuVu.idLieu" :element="lieuVu" />
  </div>
</template>

<script>
import VoirElement from "@/components/VoirElement.vue";
import axios from "axios";

export default {
  data() {
    return {
      lieux: [],
      nouveauLieu: {
        nom: null,
        adresse: null,
        capacite: null,
      },
      lieuSelectionne: null,
      lieuVu: null,
      url: "http://localhost:8080/lieux", // Adjust as needed
      isLoggedInAdmin: false,
    };
  },
  mounted() {
    this.isLoginAdmin();
    this.getLieux();
  },
  methods: {
    getLieux() {
      axios.get(this.url)
        .then(response => {
          this.lieux = response.data;
          // Update status and message handling as needed
        })
        .catch(error => console.error("There was an error loading the venues:", error));
    },
    addLieu() {
      axios.post(this.url, this.nouveauLieu)
        .then(() => {
          this.resetNouveauLieu();
          this.getLieux();
        })
        .catch(error => console.error("Error adding venue:", error));
    },
    editLieu(lieu) {
      this.lieuSelectionne = { ...lieu };
    },
    updateLieu() {
      axios.put(`${this.url}/${this.lieuSelectionne.idLieu}`, this.lieuSelectionne)
        .then(() => {
          this.lieuSelectionne = null;
          this.getLieux();
        })
        .catch(error => console.error("Error updating venue:", error));
    },
    deleteLieu(id) {
      axios.delete(`${this.url}/${id}`)
        .then(() => this.getLieux())
        .catch(error => console.error("Error deleting venue:", error));
    },
    isLoginAdmin() {
      const username = localStorage.getItem('nom');
      this.isLoggedInAdmin = username === "admin";
    },
    resetNouveauLieu() {
      this.nouveauLieu = {
        nom: null,
        adresse: null,
        capacite: null,
      };
    }
  },
  components: {
    VoirElement
  }
};
</script>

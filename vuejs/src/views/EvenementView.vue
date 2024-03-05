<template>
  <div class="evenements">
    <h1>Liste des événements</h1>
    <p id="status"></p>
    <p id="message"></p>
    <table>
      <thead>
        <tr>
          <th>IdÉvénement</th>
          <th>Nom</th>
          <th>Date</th>
          <th>Heure</th>
          <th>Durée</th>
          <th>Lieu</th>
          <th>Capacité Max</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="evenement in evenements" :key="evenement.idEvenement">
          <td>{{ evenement.idEvenement }}</td>
          <td>{{ evenement.nom }}</td>
          <td>{{ evenement.date }}</td>
          <td>{{ evenement.heure }}</td>
          <td>{{ evenement.duree }}</td>
          <td>{{ evenement.lieu }}</td>
          <td>{{ evenement.capaciteMax }}</td>
          <td>
            <button v-if="isLoggedInAdmin" @click="editEvenement(evenement)">Modifier</button>
            <button v-if="isLoggedInAdmin" @click="deleteEvenement(evenement.idEvenement)">Supprimer</button>
            <button @click="evenementVu = evenement">Voir plus</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-if="isLoggedInAdmin">
      <h2>Ajouter un événement</h2>
      <form @submit.prevent="addEvenement">
        <label>Nom :</label>
        <input type="text" v-model="nouvelEvenement.nom" required />
        <br />
        <label>Date :</label>
        <input type="date" v-model="nouvelEvenement.date" required />
        <br />
        <label>Heure :</label>
        <input type="time" v-model="nouvelEvenement.heure" required />
        <br />
        <label>Durée :</label>
        <input type="time" v-model="nouvelEvenement.duree" required />
        <br />
        <label>Lieu :</label>
        <input type="text" v-model="nouvelEvenement.lieu" required />
        <br />
        <label>Capacité Max :</label>
        <input type="number" v-model="nouvelEvenement.capaciteMax" required />
        <br />
        <button type="submit">Ajouter</button>
      </form>
    </div>
    <div v-if="evenementSelectionne">
      <h2>Modifier l'événement</h2>
      <form @submit.prevent="updateEvenement">
        <label>Nom :</label>
        <input type="text" v-model="evenementSelectionne.nom" required />
        <br />
        <label>Date :</label>
        <input type="date" v-model="evenementSelectionne.date" required />
        <br />
        <label>Heure :</label>
        <input type="time" v-model="evenementSelectionne.heure" required />
        <br />
        <label>Durée :</label>
        <input type="time" v-model="evenementSelectionne.duree" required />
        <br />
        <label>Lieu :</label>
        <input type="text" v-model="evenementSelectionne.lieu" required />
        <br />
        <label>Capacité Max :</label>
        <input type="number" v-model="evenementSelectionne.capaciteMax" required />
        <br />
        <button type="submit">Modifier</button>
        <button @click="evenementSelectionne = null">Annuler</button>
      </form>
    </div>
    <VoirElement
      v-if="evenementVu"
      :key="evenementVu.idEvenement"
      :element="evenementVu"
    />
  </div>
</template>

<script>
import VoirElement from "@/components/VoirElement.vue";
import axios from "axios";

export default {
  data() {
    return {
      evenements: [],
      nouvelEvenement: {
        nom: null,
        date: null,
        heure: null,
        duree: null,
        lieu: null,
        capaciteMax: null,
      },
      evenementSelectionne: null,
      evenementVu: null,
      url: "http://localhost:8080/evenements", // Adjust as needed
      isLoggedInAdmin: false,
    };
  },
  mounted() {
    this.isLoginAdmin();
    this.getEvenements();
  },
  methods: {
    getEvenements() {
      axios.get(this.url)
        .then(response => {
          this.evenements = response.data;
          // Update status and message handling as needed
        })
        .catch(error => console.error("There was an error loading the events:", error));
    },
    addEvenement() {
      axios.post(this.url, this.nouvelEvenement)
        .then(() => {
          this.resetNouvelEvenement();
          this.getEvenements();
        })
        .catch(error => console.error("Error adding event:", error));
    },
    editEvenement(evenement) {
      this.evenementSelectionne = { ...evenement };
    },
    updateEvenement() {
      axios.put(`${this.url}/${this.evenementSelectionne.idEvenement}`, this.evenementSelectionne)
        .then(() => {
          this.evenementSelectionne = null;
          this.getEvenements();
        })
        .catch(error => console.error("Error updating event:", error));
    },
    deleteEvenement(id) {
      axios.delete(`${this.url}/${id}`)
        .then(() => this.getEvenements())
        .catch(error => console.error("Error deleting event:", error));
    },
    isLoginAdmin() {
      const username = localStorage.getItem('nom');
      this.isLoggedInAdmin = username === "admin";
    },
    resetNouvelEvenement() {
      this.nouvelEvenement = {
        nom: null,
        date: null,
        heure: null,
        duree: null,
        lieu: null,
        capaciteMax: null,
      };
    }
  },
  components: {
    VoirElement
  }
};
</script>

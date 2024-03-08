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
            <button class="rouge" @click="supprimerLieu(lieu.id)">
              Supprimer
            </button>
            <button @click="updateLieu(lieu)">Mettre à jour</button>
          </td>
        </tr>
      </tbody>
    </table>
    <button @click="afficherFormulaire = !afficherFormulaire">
      Ajouter Lieu
    </button>
    <div v-if="afficherFormulaire" class="formulaire-ajout">
      <h2>
        {{
          modeMiseAJour ? "Mettre à jour le lieu" : "Ajouter un nouveau lieu"
        }}
      </h2>
      <form @submit.prevent="modeMiseAJour ? mettreAJourLieu() : ajouterLieu()">
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
        <button type="submit">
          {{ modeMiseAJour ? "Mettre à jour" : "Enregistrer" }}
        </button>
        <button @click="reinitialiserFormulaire" type="button">Annuler</button>
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
    updateLieu(lieu) {
      this.nouveauLieu = { ...lieu };
      this.afficherFormulaire = true;
      this.modeMiseAJour = true;
    },
    mettreAJourLieu() {
      axios
        .put(`${this.url}/${this.nouveauLieu.id}`, this.nouveauLieu)
        .then(() => {
          this.chargerLieux();
          this.reinitialiserFormulaire();
        })
        .catch((error) => {
          console.error("Erreur lors de la mise à jour du lieu", error);
        });
    },
    reinitialiserFormulaire() {
      this.nouveauLieu = {
        id: null,
        nom: "",
        adresse: "",
        capaciteAccueil: null,
      };
      this.afficherFormulaire = false;
      this.modeMiseAJour = false;
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

<style>
.lieux {
  font-family: Arial, sans-serif;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1,
h2 {
  color: #333;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th,
td {
  text-align: left;
  padding: 8px;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f2f2f2;
}

button {
  cursor: pointer;
  padding: 5px 10px;
  margin-right: 5px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

button.rouge {
  background-color: #dc3545;
}

button.rouge:hover {
  background-color: #c82333;
}

/* Style pour le formulaire */
form {
  margin-top: 20px;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
}

form label {
  display: block;
  margin-bottom: 5px;
}

form input,
form select {
  width: 100%;
  padding: 8px;
  margin-bottom: 20px;
  border-radius: 4px;
  border: 1px solid #ccc;
}

form button {
  width: auto;
  padding: 10px 15px;
  background-color: #28a745;
}

form button:hover {
  background-color: #218838;
}

/* Annuler bouton style */
button[type="button"] {
  background-color: #6c757d;
}

button[type="button"]:hover {
  background-color: #5a6268;
}
</style>

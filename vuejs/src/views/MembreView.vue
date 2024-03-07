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
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="evenement in evenementsDuMembre" :key="evenement.id">
            <td>{{ evenement.nom }}</td>
            <td>{{ evenement.date_heure }}</td>
            <td>
              <button @click="desinscrireMembre(evenement)" class="rouge">
                Désinscrire
              </button>
            </td>
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

    <!-- Bouton pour afficher/masquer le formulaire -->
    <button @click="afficherFormulaire">
      {{ afficherNouveauMembre ? "Masquer" : "Afficher" }} le formulaire
    </button>

    <!-- Formulaire d'ajout de membre -->
    <div v-if="afficherNouveauMembre">
      <h2>Ajouter un membre</h2>
      <form @submit.prevent="ajouterMembre">
        <label for="nom">Nom:</label>
        <input type="text" id="nom" v-model="nouveauMembre.nom" required />

        <label for="prenom">Prénom:</label>
        <input
          type="text"
          id="prenom"
          v-model="nouveauMembre.prenom"
          required
        />

        <label for="adresse">Adresse:</label>
        <input
          type="text"
          id="adresse"
          v-model="nouveauMembre.adresse"
          required
        />

        <label for="age">Âge:</label>
        <input type="number" id="age" v-model="nouveauMembre.age" required />

        <label for="motDePasse">Mot de passe:</label>
        <input
          type="password"
          id="motDePasse"
          v-model="nouveauMembre.motDePasse"
          required
        />

        <button type="submit">Ajouter Membre</button>
      </form>
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
      nouveauMembre: {
        nom: "",
        prenom: "",
        adresse: "",
        age: 0,
        motDePasse: "",
      },
      afficherNouveauMembre: false,
      membreActuel: null, // Ajoutez membreActuel comme propriété de données
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
      this.membreActuel = this.membres.find((membre) => membre.id === idMembre);
      this.evenementsCharge = true;
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
          this.evenementsCharge = false;
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
    afficherFormulaire() {
      this.afficherNouveauMembre = !this.afficherNouveauMembre;
    },
    ajouterMembre() {
      axios
        .post("/api/v1/membre/ajouter", this.nouveauMembre)
        .then(() => {
          this.nouveauMembre = {
            nom: "",
            prenom: "",
            adresse: "",
            age: 0,
            motDePasse: "",
          };
          this.chargerMembres();
          this.afficherNouveauMembre = false; // Cacher le formulaire après l'ajout
        })
        .catch((error) =>
          console.error("Erreur lors de l'ajout du membre", error)
        );
    },
    desinscrireMembre(evenement) {
      console.log("Evenement:", evenement);

      if (this.membreActuel && evenement && evenement.id_evnt) {
        const idMembre = this.membreActuel.id;
        const idEvenement = evenement.id_evnt;
        console.log("ID Membre:", idMembre);
        console.log("ID Evenement:", idEvenement);

        axios
          .post(
            `/api/v1/membre/deseinscrireDeEvent?membreId=${idMembre}&eventId=${idEvenement}`
          )
          .then(() => {
            this.voirEvenements(idMembre);
          })
          .catch((error) => {
            console.error(
              "Erreur lors de la désinscription du membre de l'événement",
              error
            );
          });
      } else {
        console.error(
          "ID du membre ou de l'événement non défini lors de la désinscription"
        );
      }
    },
  },
  mounted() {
    this.chargerMembres();
  },
};
</script>

<style scoped>
.membres {
  margin: 20px;
  padding: 20px;
  border: 1px solid #ccc;
}

form {
  margin-top: 20px;
  padding: 10px;
  border: 1px solid #ddd;
  background-color: #f9f9f9;
}

.rouge {
  background-color: red;
  color: white;
}
</style>

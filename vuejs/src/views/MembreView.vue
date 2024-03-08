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
            <button @click="setMembreActuel(membre.id)">
              {{ isMembreInscrit(membre.id) ? "Événements" : "Inscription" }}
            </button>
          </td>
          <td>
            <button class="rouge" @click="supprimerMembre(membre.id)">
              Supprimer
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Table pour afficher les événements -->
    <div v-if="evenementsDuMembre.length > 0">
      <h2>
        Événements du membre {{ membreActuel.nom }} {{ membreActuel.prenom }}
      </h2>
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
          <tr>
            <button @click="afficherFormulaireInscription()">Inscrire</button>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else-if="evenementsDuMembre.length === 0">
      <p>Aucun événement pour le membre.</p>
    </div>

    <!-- Bouton pour afficher/masquer le formulaire -->
    <button @click="afficherFormulaire">
      {{ afficherNouveauMembre ? "Masquer" : "Afficher" }} le formulaire
    </button>

    <!-- Formulaire d'ajout de membre -->
    <div v-if="afficherNouveauMembre">
      <h2>Ajouter un membre</h2>
      <form @submit.prevent="ajouterMembre">
        <div class="form-group col-md-6">
          <label for="nom">Nom:</label>
          <input type="text" id="nom" v-model="nouveauMembre.nom" required />
        </div>

        <div class="form-group col-md-6">
          <label for="prenom">Prénom:</label>
          <input
            type="text"
            id="prenom"
            v-model="nouveauMembre.prenom"
            required
          />
        </div>

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

    <div v-if="showInscriptionForm">
      <!-- Form lier un membre à un événement -->
      <form @submit.prevent="inscrireMembre(membreActuel.id, selectedEvent)">
        <label for="eventSelect">Sélectionner un événement : </label>
        <select v-model="selectedEvent" id="eventSelect" required>
          <option v-for="event in allEvents" :key="event.id" :value="event.id">
            {{ event.nom }}
          </option>
        </select>

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
      membres: [],
      evenementsDuMembre: [],
      nouveauMembre: {
        nom: "",
        prenom: "",
        adresse: "",
        age: 0,
        motDePasse: "",
      },
      afficherNouveauMembre: false,
      membreActuel: null, // Ajoutez membreActuel comme propriété de données
      allEvents: [],
      selectedEvent: null,
      showInscriptionForm: false,
    };
  },
  methods: {
    async chargerMembres() {
      try {
        const membersResponse = await axios.get("/api/v1/membre/lister");
        const members = membersResponse.data;

        // GET des evenements pour chaque memebre
        await Promise.all(
          members.map(async (membre) => {
            try {
              const eventsResponse = await axios.get(
                `/api/v1/membre/${membre.id}/evenements`
              );
              membre.evenementsDuMembre = eventsResponse.data;
            } catch (error) {
              console.error(
                `Erreur lors du chargement des événements du membre ${membre.id}`,
                error
              );
              membre.evenementsDuMembre = [];
            }
          })
        );

        // GET de tout les événement disponibles
        const allEventsResponse = await axios.get(
          "/api/v1/evenement/getAllEvenement"
        );
        this.allEvents = allEventsResponse.data;

        // Les membres
        this.membres = members;
      } catch (error) {
        console.error("Erreur Lors du chargement des membres", error);
      }
    },

    setMembreActuel(idMembre) {
      try {
        this.membreActuel = this.membres.find(
          (membre) => membre.id === idMembre
        );
        this.evenementsDuMembre = this.membreActuel.evenementsDuMembre || [];

        this.evenementsDuMembre.length > 0
          ? (this.showInscriptionForm = false)
          : (this.showInscriptionForm = true);
      } catch (error) {
        console.error(
          "Erreur lors du chargement des événements du membre",
          error
        );
      }
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
            this.setMembreActuel(idMembre);
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

    isMembreInscrit(memberId) {
      try {
        const membre = this.membres.find((m) => m.id === memberId);
        return membre ? membre.evenementsDuMembre.length > 0 : false;
      } catch (error) {
        console.error("An error occurred in isMembreInscrit", error);
        return false;
      }
    },

    inscrireMembre(memberId, eventId) {
      try {
        // Ensure both member and event are selected
        if (memberId && eventId) {
          // Update your API endpoint to match the working one
          const apiUrl = `/api/v1/membre/registerToEvent?membreId=${memberId}&eventId=${eventId}`;

          // Perform the linking operation using your updated API endpoint
          axios
            .post(apiUrl)
            .then(() => {
              // Optionally, you can update your data or show a success message
              console.log("Member linked to event successfully");
            })
            .catch((error) => {
              console.error("Error linking member to event", error);
            });
        } else {
          console.error("Both member and event must be selected.");
        }
      } catch (error) {
        console.error("An unexpected error occurred", error);
      }
    },

    afficherFormulaireInscription() {
      console.log("afficherFormulaireInscriotion clicked");
      this.showInscriptionForm = true;
      this.selectedEvent = null;
    },
  },

  mounted() {
    this.chargerMembres();
  },
};
</script>

<style scoped>
/* Style de base pour le conteneur principal */
.membres,
.evenements {
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

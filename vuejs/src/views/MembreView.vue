<template>
  <div class="membres">
    <h1>Liste des membres</h1>
    <table>
      <thead>
        <tr>
          <th>Nom</th>
          <th>Prénom</th>
          <th>Âge</th>
          <th>Adresse</th>
          <th>Événements Inscrits</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="membre in membres" :key="membre.id">
          <td>{{ membre.nom }}</td>
          <td>{{ membre.prenom }}</td>
          <td>{{ membre.age }}</td>
          <td>{{ membre.adresse }}</td>
          <td>
            <button @click="voirEvenements(membre)">Voir Événements</button>
          </td>
          <td>
            <button @click="modifierMembre(membre)">Modifier</button>
            <button @click="supprimerMembre(membre.id)">Supprimer</button>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- Ajout et modification d'un membre -->
    <div v-if="membreEdition">
      <h2>{{ membreEdition.id ? 'Modifier' : 'Ajouter' }} un membre</h2>
      <form @submit.prevent="sauvegarderMembre">
        <label>Nom :</label>
        <input type="text" v-model="membreEdition.nom" required />
        <br />
        <label>Prénom :</label>
        <input type="text" v-model="membreEdition.prenom" required />
        <br />
        <label>Âge :</label>
        <input type="number" v-model="membreEdition.age" />
        <br />
        <label>Adresse :</label>
        <input type="text" v-model="membreEdition.adresse" />
        <br />
        <button type="submit">Sauvegarder</button>
        <button @click="membreEdition = null">Annuler</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      membres: [],
      membreEdition: null, // Pour l'ajout ou la modification d'un membre
    };
  },
  mounted() {
    this.chargerMembres();
  },
  methods: {
    chargerMembres() {
      // Remplacer 'votreEndpointMembres' par l'URL réelle de votre API
      axios.get('http://localhost:8080/api/v1/membre')
        .then(response => {
          this.membres = response.data;
        })
        .catch(error => {
          console.error("Il y a eu une erreur lors du chargement des membres :", error);
        });
    },
    voirEvenements(membre) {
      // Ici, vous pouvez naviguer vers un autre composant/vue pour afficher les événements auxquels le membre est inscrit
      // ou ouvrir une modal avec ces informations.
      console.log("Voir les événements pour", membre.nom);
    },
    modifierMembre(membre) {
      // Fait une copie de l'objet membre pour l'édition
      this.membreEdition = { ...membre };
    },
    supprimerMembre(id) {
      // Remplacer 'votreEndpointSupprimerMembre' par l'URL réelle de votre API
      axios.delete(`http://localhost:8080/api/v1/membre/${id}`)
        .then(() => {
          this.chargerMembres(); // Recharger la liste après suppression
        })
        .catch(error => {
          console.error("Erreur lors de la suppression du membre :", error);
        });
    },
    sauvegarderMembre() {
      const url = this.membreEdition.id ? 
        `http://localhost:8080/api/v1/membre/${this.membreEdition.id}` :
        'http://localhost:8080/api/v1/membre';

      // Utiliser POST pour ajouter et PUT pour modifier, selon si `membreEdition.id` est défini
      const method = this.membreEdition.id ? 'put' : 'post';

      axios[method](url, this.membreEdition)
        .then(() => {
          this.membreEdition = null;
          this.chargerMembres();
        })
        .catch(error => {
          console.error("Erreur lors de la sauvegarde du membre :", error);
        });
    }
  }
};
</script>

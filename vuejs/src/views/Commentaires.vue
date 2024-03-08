<template>
  <div class="commentaires">
    <h1>Liste des Commentaires</h1>

    <!-- Form to add a new comment -->
    <form @submit.prevent="ajouterCommentaire">
      <label>
        Texte:
        <input type="text" v-model="nouveauCommentaire.texte" required />
      </label>
      <label>
        Membre ID:
        <input type="number" v-model="nouveauCommentaire.membreId" required />
      </label>
      <label>
        Evenement ID:
        <input type="number" v-model="nouveauCommentaire.evenementId" required />
      </label>
      <button type="submit">Ajouter Commentaire</button>
    </form>

    <!-- Table to display comments -->
    <div v-if="commentaires.length > 0">
      <table>
        <thead>
        <tr>
          <th>Texte</th>
          <th>Membre ID</th>
          <th>Evenement ID</th>
          <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="commentaire in commentaires" :key="commentaire._id">
          <td>{{ commentaire.texte }}</td>
          <td>{{ commentaire.membreId }}</td>
          <td>{{ commentaire.evenementId }}</td>
          <td>
            <button @click="setCommentaireId(commentaire._id); supprimerCommentaire()" style="color: red;">Supprimer</button>

          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div v-else>
      <p>Aucun commentaire trouvé.</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      commentaires: [],
      nouveauCommentaire: {
        texte: "",
        membreId: null,
        evenementId: null,
      },
      commentaireId: null,
    };
  },
  methods: {

    setCommentaireId(commentaireId) {
      this.commentaireId = commentaireId;
    },
    chargerCommentaires() {
      axios
          .get("http://localhost:8080/projet_sys_info/ajouterCommentaire")
          .then((response) => {
            this.commentaires = response.data;
          })
          .catch((error) => {
            console.error("Erreur lors du chargement des commentaires", error);
          });
    },
    ajouterCommentaire() {
      // Effectuez la requête pour vérifier l'inscription
      const url = `/api/v1/membre/${this.nouveauCommentaire.membreId}/evenements`;
      axios.get(url, { mode: 'no-cors' })
          .then((response) => {
            // Mettez à jour la variable isMemberRegistered en fonction de la réponse
            const evenementsInscrits = response.data;

            // Récupérez l'ID de l'événement que vous souhaitez vérifier
            const evenementAVerifier = this.nouveauCommentaire.evenementId;

            // Vérifiez si le membre est inscrit à l'événement en question
            this.isMemberRegistered = evenementsInscrits.some(event => event.id_evnt === evenementAVerifier);

            // Si le membre est inscrit, ajoutez le commentaire
            if (this.isMemberRegistered) {
              axios
                  .post("http://localhost:8080/projet_sys_info/ajouterCommentaire", this.nouveauCommentaire)
                  .then((response) => {
                    if (response.data.success) {
                      console.log("Commentaire ajouté avec succès!");
                      // Clear the form and reload comments
                      this.nouveauCommentaire = { texte: "", membreId: null, evenementId: null };
                      this.chargerCommentaires();
                    } else {
                      console.error("Erreur lors de l'ajout du commentaire", response.data.message);
                    }
                  })
                  .catch((error) => {
                    console.error("Erreur lors de l'ajout du commentaire", error);
                  });
            }
          })
          .catch((error) => {
            console.error("Erreur lors de la vérification de l'inscription", error);
          });
    },

    supprimerCommentaire() {
      axios
          .delete(`http://localhost:8080/projet_sys_info/ajouterCommentaire/${this.commentaireId}`)
          .then((response) => {
            if (response.data.success) {
              console.log("Commentaire supprimé avec succès!");
              // Reload comments after successful deletion
              this.chargerCommentaires();
            } else {
              console.error("Erreur lors de la suppression du commentaire", response.data.message);
            }
          })
          .catch((error) => {
            console.error("Erreur lors de la suppression du commentaire", error);
          });
    },

  },
  mounted() {
    this.chargerCommentaires();
  },
};
</script>

<style scoped>
.commentaires {
  margin: 20px;
  padding: 20px;
  border: 1px solid #ccc;
}

/* Style for the form and table */
form,
table {
  margin-top: 20px;
}

/* Style for the table */
table {
  width: 100%;
  border-collapse: collapse;
}

table,
th,
td {
  border: 1px solid #ddd;
}

th,
td {
  padding: 8px;
  text-align: left;
}

/* Style for the delete button */
button {
  cursor: pointer;
}
</style>

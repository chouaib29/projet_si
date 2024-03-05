<template>
  <div class="commentaires">
    <h1>Commentaires sur l'événement</h1>
    <div v-if="isLoggedIn" class="nouveau-commentaire">
      <h2>Ajouter un commentaire</h2>
      <form @submit.prevent="ajouterCommentaire">
        <textarea v-model="nouveauCommentaire.texte" placeholder="Votre commentaire" required></textarea>
        <br>
        <button type="submit">Publier</button>
      </form>
    </div>
    <div v-else>
      <p>Veuillez vous <router-link to="/connexion">connecter</router-link> pour laisser un commentaire.</p>
    </div>
    <div class="liste-commentaires">
      <h2>Commentaires</h2>
      <div v-for="commentaire in commentaires" :key="commentaire.id" class="commentaire">
        <p><strong>{{ commentaire.auteur }}</strong> dit :</p>
        <p>{{ commentaire.texte }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoggedIn: false, // Supposons une propriété pour vérifier si l'utilisateur est connecté
      nouveauCommentaire: {
        texte: ''
      },
      commentaires: [
        // Exemple de données de commentaire
        // { id: 1, texte: "Un super événement!", auteur: "Alice" },
        // { id: 2, texte: "J'ai vraiment apprécié.", auteur: "Bob" }
      ]
    };
  },
  mounted() {
    this.verifierConnexion();
    this.chargerCommentaires();
  },
  methods: {
    verifierConnexion() {
      // Implémentez la logique pour vérifier si un utilisateur est connecté
      // this.isLoggedIn = ...
    },
    chargerCommentaires() {
      // Chargez les commentaires depuis votre backend ou une source de données
      // axios.get('/api/commentaires').then(response => {
      //   this.commentaires = response.data;
      // });
    },
    ajouterCommentaire() {
      // Ajoutez le nouveau commentaire à votre backend ou une source de données
      // axios.post('/api/commentaires', this.nouveauCommentaire).then(() => {
      //   this.nouveauCommentaire.texte = '';
      //   this.chargerCommentaires(); // Recharger les commentaires pour afficher le nouveau
      // });
    }
  }
};
</script>

<style scoped>
.commentaires {
  /* Styles pour vos commentaires */
}
.nouveau-commentaire form {
  /* Styles pour le formulaire de nouveau commentaire */
}
.liste-commentaires {
  /* Styles pour la liste des commentaires */
}
</style>

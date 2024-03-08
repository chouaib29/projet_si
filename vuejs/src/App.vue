<template>
  <nav>
    <router-link to="/">Accueil</router-link> |
    <router-link to="/membres">Membres</router-link> |
    <router-link to="/evenements">Evenements</router-link> |
    <router-link to="/lieux">Lieux</router-link> |
    <router-link to="/commentaires">Commentaires</router-link> |
  </nav>
  <router-view />
</template>

<script>
export default {
  data() {
    return {
      isLoggedInAdmin: "",
      isLoggedIn: "",
    };
  },
};
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #28a745;
    }
  }
}
</style>

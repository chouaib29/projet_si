import { createRouter, createWebHistory } from "vue-router";
import AccueilView from "../views/AccueilView.vue";

const routes = [
  {
    path: "/",
    name: "accueil",
    component: AccueilView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

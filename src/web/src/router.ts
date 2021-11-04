import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "./views/Home.vue";
import Login from "./views/Login.vue";
import Register from "./views/Register.vue";
import Profile from "./views/Profile.vue";
import Dashboard from "./views/Dashboard.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/login/",
    name: "login",
    component: Login,
  },
  {
    path: "/profile/",
    name: "profile",
    component: Profile,
  },
  {
    path: "/register/",
    name: "register",
    component: Register,
  },
  {
    path: "/dashboard/",
    name: "dashboard",
    component: Dashboard,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

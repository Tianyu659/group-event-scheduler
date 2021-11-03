<template>
  <div id="navigation">
    <router-link to="/">Home</router-link>
    <router-link to="/about">About</router-link>
    <div class="spacer" />
    <router-link to="/login">Login</router-link>
  </div>
  <router-view id="content" />
</template>

<style lang="scss" src="./styles/index.scss"></style>

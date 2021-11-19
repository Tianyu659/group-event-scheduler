<template>
  <div class="invitation">
    <h3>
      Invitation to {{ invitation.user.firstName }}
      {{ invitation.user.lastName }}
    </h3>
    <div v-if="invitation.response">
      <ul class="fancy" v-if="invitation.response.accepted">
        <li v-for="event of invitation.response.events" :key="event">
          {{ event.event.name }}:
          <span v-if="event.available">
            Available, interest {{ event.interest }}/5
          </span>
          <span v-else>Unavailable</span>
        </li>
      </ul>
      <p v-else>User is unavailable!</p>
    </div>
    <div v-else>No response!</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { Invitation } from "@/models/groupDate";
import GroupDateFormEvent from "@/views/GroupDateFormEvent.vue";
import { Prop } from "vue-property-decorator";

@Options({
  components: { GroupDateFormEvent },
})
export default class Home extends Vue {
  public readonly session = session;
  @Prop() public invitation!: Invitation;
}
</script>

<style lang="scss" scoped>
.invitation {
  border: 1px solid lightgray;
  padding: 1em;
  border-radius: 0.25rem;

  .location {
    font-weight: bold;
  }

  & > *:first-child {
    margin-top: 0;
  }

  & > *:last-child {
    margin-bottom: 0;
  }

  margin-bottom: 1rem;
}
</style>

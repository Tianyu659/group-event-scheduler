<template>
  <div>
    <div v-if="groupDate !== null">
      <h1>{{ groupDate.name }}</h1>
      <p>{{ groupDate.description }}</p>
      <h2>Events</h2>
      <div>
        <group-date-details-event
          v-for="event of groupDate.events"
          :group-date-event="event"
          :key="event.id"
        />
      </div>
      <h2>Invitations</h2>
      <div>
        <group-date-details-invitation
          v-for="invitation of invitations"
          :invitation="invitation"
          :key="invitation.id"
        />
      </div>
    </div>
    <div v-else>Loading...</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { url } from "@/url";
import { GroupDate, Invitation } from "@/models/groupDate";
import GroupDateDetailsInvitation from "@/views/GroupDateDetailsInvitation.vue";
import GroupDateDetailsEvent from "@/views/GroupDateDetailsEvent.vue";

@Options({
  components: {
    GroupDateDetailsEvent,
    GroupDateDetailsInvitation,
  },
})
export default class Home extends Vue {
  public readonly session = session;
  public groupDate: GroupDate | null = null;
  public invitations: Array<Invitation> = [];

  public created(): void {
    const id = this.$route.params["id"];
    fetch(url(`/groupDates/${id}`), {
      method: "GET",
      headers: { Authorization: session.token! },
    }).then((response: Response) => {
      response.json().then((data: Record<string, any>) => {
        this.groupDate = GroupDate.wrap(data);
      });
    });
    fetch(url(`/groupDates/${id}/invitations`), {
      method: "GET",
      headers: { Authorization: session.token! },
    }).then((response: Response) => {
      response.json().then((data: Array<Record<string, any>>) => {
        this.invitations.length = 0;
        this.invitations.push(...data.map(Invitation.wrap));
      });
    });
  }
}
</script>

<style scoped></style>

<template>
  <div>
    <div v-if="groupDate !== null">
      <h1>{{ groupDate.name }}</h1>
      <p>{{ groupDate.description }}</p>
      <div
        class="form"
        v-if="!groupDate.finalized && groupDate.creator.id === session.user.id"
      >
        <button class="primary" @click="finalize">Finalize</button>
      </div>
      <h2>Events</h2>
      <div>
        <group-date-details-event
          v-for="event of groupDate.events"
          :group-date="groupDate"
          :group-date-event="event"
          :key="event.id"
          :class="{ best: event === groupDate.getBestEvent() }"
          @delete="onDeleteEvent(event)"
        />
      </div>
      <h2>Invitations</h2>
      <div>
        <group-date-details-invitation
          v-for="invitation of invitations"
          :group-date="groupDate"
          :invitations="invitations"
          :invitation="invitation"
          :key="invitation.id"
          @delete="onDeleteInvitation(invitation)"
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
import { GroupDate, GroupDateEvent, Invitation } from "@/models/groupDate";
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

  public refresh(): void {
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

  public created(): void {
    this.refresh();
  }

  public finalize(): void {
    fetch(url(`/groupDates/${this.groupDate!.id}`), {
      method: "PUT",
      headers: { Authorization: session.token! },
      body: JSON.stringify({ live: this.groupDate!.live, finalized: true }),
    }).then((response: Response) => {
      if (response.status === 200) {
        this.groupDate!.finalized = true;
      }
    });
  }

  public onDeleteEvent(event: GroupDateEvent): void {
    for (let i = 0; i < this.groupDate!.events.length; ++i) {
      if (this.groupDate!.events[i] === event) {
        this.groupDate!.events.splice(i, 1);
        this.refresh();
        return;
      }
    }
  }

  public onDeleteInvitation(invitation: Invitation): void {
    for (let i = 0; i < this.invitations.length; ++i) {
      if (this.invitations[i] === invitation) {
        this.invitations.splice(i, 1);
        this.refresh();
        return;
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.event.best {
  background-color: mix(lightgreen, white, 25%);
}
</style>

<template>
  <div class="form">
    <div class="group">
      <input
        id="name"
        type="text"
        placeholder="Group date name"
        v-model="groupDate.name"
      />
      <label>Name</label>
    </div>
    <div class="group">
      <input
        id="description"
        type="text"
        placeholder="Group date description"
        v-model="groupDate.description"
      />
      <label>Description</label>
    </div>
    <div>
      <h3>Events</h3>
      <group-date-form-event
        v-for="groupDateEvent of groupDate.events"
        :group-date-event="groupDateEvent"
        :key="groupDateEvent"
      />
      <div v-if="groupDate.events.length === 0">
        You haven't added any events!
      </div>
      <group-date-form-event-search
        :group-date="groupDate"
        @submit="onCreateEvent"
      />
    </div>
    <div>
      <h3>Invitations</h3>
      <group-date-form-invitation
        v-for="invitation of groupDate.invitations"
        :invitation="invitation"
        :key="invitation"
      />
      <div v-if="groupDate.invitations.length === 0">
        You haven't invited anyone!
      </div>
      <group-date-form-invitation-form
        :group-date="groupDate"
        @submit="onCreateInvitation"
      />
    </div>
    <div>
      <h3>Finalize</h3>
      <button @click="onClickSubmit">Create my Group Date</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { GroupDate, GroupDateEvent, Invitation } from "@/models/groupDate";
import GroupDateFormEvent from "@/views/GroupDateFormEvent.vue";
import GroupDateFormEventForm from "@/views/GroupDateFormEventForm.vue";
import GroupDateFormInvitationForm from "@/views/GroupDateFormInvitationForm.vue";
import GroupDateFormInvitation from "@/views/GroupDateFormInvitation.vue";
import GroupDateFormEventSearch from "@/views/GroupDateFormEventSearch.vue";
import { url } from "@/url";

@Options({
  components: {
    GroupDateFormEventSearch,
    GroupDateFormInvitation,
    GroupDateFormInvitationForm,
    GroupDateFormEventForm,
    GroupDateFormEvent,
  },
})
export default class GroupDateForm extends Vue {
  public groupDate: GroupDate = GroupDate.empty(session.user!);

  public onCreateEvent(groupDateEvent: GroupDateEvent): void {
    this.groupDate.events.push(groupDateEvent);
  }

  public onCreateInvitation(invitation: Invitation): void {
    this.groupDate.invitations.push(invitation);
  }

  public onClickSubmit(): void {
    fetch(url("/groupDates/"), {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: session.token!,
      },
      body: JSON.stringify(this.groupDate.dump()),
    }).then((response: Response) => {
      response.json().then((data: Record<string, never>) => {
        if (response.status === 201) {
          console.log(data);
        } else {
          console.error(data);
        }
      });
    });
  }
}
</script>

<style scoped></style>

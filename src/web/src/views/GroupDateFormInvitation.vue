<template>
  <div class="invitation">
    <span>
      Invited {{ invitation.user.firstName }} {{ invitation.user.lastName }} ({{
        invitation.user.username
      }})
    </span>
    <span class="float-right cursor-pointer" @click="onClickDelete">
      &#x2715;
    </span>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import { GroupDate, Invitation } from "@/models/groupDate";

@Options({})
export default class GroupDateFormInvitation extends Vue {
  @Prop() public groupDate!: GroupDate;
  @Prop() public invitation!: Invitation;

  public onClickDelete(): void {
    for (let i = 0; i < this.groupDate.invitations.length; ++i) {
      if (this.groupDate.invitations[i] === this.invitation) {
        this.groupDate.invitations.splice(i, 1);
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.invitation {
  border: 1px solid lightgray;
  padding: 1em;
  border-radius: 0.25rem;
}
</style>

<template>
  <div class="invitation">
    Invited {{ invitation.user.firstName }} {{ invitation.user.lastName }} ({{
      invitation.user.username
    }})
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import { Invitation } from "@/models/groupDate";

@Options({})
export default class GroupDateFormInvitation extends Vue {
  @Prop() public invitation!: Invitation;
}
</script>

<style lang="scss" scoped>
.invitation {
  border: 1px solid lightgray;
  padding: 1em;
  border-radius: 0.25rem;
}
</style>

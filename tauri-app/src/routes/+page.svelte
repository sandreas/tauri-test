<script lang="ts">
  import { onMount } from 'svelte';

  let log = "";
  window.addEventListener(
          "message",
          (event) => {
            log += "message received: " + event.data + "\n"
          },
          false,
  );


  onMount(() => {
    window.postMessage("message from svelte");
  });
</script>

<main class="container">
  <h1>Welcome to Tauri + Svelte</h1>
  <pre class="log">{log}</pre>

</main>

<style>
  .container {
    margin:0;
    padding:0;
  }
.log {
  margin:0;
  padding:0;
  position:absolute;
  bottom:0;
  left:0;
  right:0;
  border:1px dotted black;
  min-height:50px;
}
</style>

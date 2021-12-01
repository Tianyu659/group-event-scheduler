export function load(): Promise<void> {
  if (document.readyState === "complete") {
    return Promise.resolve();
  } else {
    return new Promise((resolve: () => void) => {
      if (document.readyState === "complete") {
        resolve();
      } else {
        window.addEventListener("load", resolve);
      }
    });
  }
}

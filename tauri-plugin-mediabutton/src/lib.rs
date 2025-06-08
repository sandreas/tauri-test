use tauri::{
  plugin::{Builder, TauriPlugin},
  Manager, Runtime,
};

pub use models::*;

#[cfg(desktop)]
mod desktop;
#[cfg(mobile)]
mod mobile;

mod commands;
mod error;
mod models;

pub use error::{Error, Result};

#[cfg(desktop)]
use desktop::Mediabutton;
#[cfg(mobile)]
use mobile::Mediabutton;

/// Extensions to [`tauri::App`], [`tauri::AppHandle`] and [`tauri::Window`] to access the mediabutton APIs.
pub trait MediabuttonExt<R: Runtime> {
  fn mediabutton(&self) -> &Mediabutton<R>;
}

impl<R: Runtime, T: Manager<R>> crate::MediabuttonExt<R> for T {
  fn mediabutton(&self) -> &Mediabutton<R> {
    self.state::<Mediabutton<R>>().inner()
  }
}

/// Initializes the plugin.
pub fn init<R: Runtime>() -> TauriPlugin<R> {
  Builder::new("mediabutton")
    .invoke_handler(tauri::generate_handler![commands::ping])
    .setup(|app, api| {
      #[cfg(mobile)]
      let mediabutton = mobile::init(app, api)?;
      #[cfg(desktop)]
      let mediabutton = desktop::init(app, api)?;
      app.manage(mediabutton);
      Ok(())
    })
    .build()
}

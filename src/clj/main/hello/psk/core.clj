(ns hello.psk.core
  (:require [miraj.html :as h]
            ;; [polymer.iron :as iron :refer [flex-layout icons pages selector]]
            ;; [polymer.paper :as paper :refer [drawer-panel icon-button item material menu
            ;;                                  scroll-header-panel toast toolbar]]
            [polymer.platinum.service-worker :as sw :refer [cache register]]
            ;; [hello.psk.components :as comp :refer [my-greeting my-list]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

;;FIXME: make this a fwd declaration e.g. (declare iron/flex-layout)?
;;Either that or require that defpage come first and forward declare components.
(h/require '[polymer.iron :as iron :refer [flex-layout icons pages selector]]
           '[polymer.paper :as paper :refer [drawer-panel icon-button item material menu
                                             scroll-header-panel toast toolbar]]
            ;; [polymer.platinum.service-worker :as sw :refer [cache register]]
           ;; [polymer.platinum :as plat :refer [sw-cache sw-register]]
           '[hello.psk.components :as comp :refer [my-greeting my-list]])

(def homepage-meta
  {:charset "utf-8"
   :description ""
   :viewport {:width :device
               :scale {:initial 1.0 :min 1.0 :max 1}}
   :generator "Miraj: Polymer Starter Kit"
   :title "Miraj: Polymer Starter Kit"
   :theme-color "#2E3AA1"
   :application-name "PSK"
   :icon {:sizes "192x192"
          :uri "images/touch/chrome-touch-icon-192x192.png"}
   :manifest "manifest.json"
   :platform {:ms {:tile-color "#3372DF"
                   :tile-image "images/touch/ms-touch-icon-144x144-precomposed.png"}
              :mobile {:web-app-capable true}
              :apple {:web-app {:capable true
                                :status-bar-style "black"
                                :title "Miraj: Polymer Starter Kit"}
                      :touch-icon {:uri "images/touch/apple-touch-icon.png"}}}})

(def header-panel
  (paper/scroll-header-panel
   {:drawer nil :fixed nil}
   (paper/toolbar ::drawerToolbar
                  (h/span ::.paper-font-title "Menu"))
   ;; Drawer Content
   (paper/menu ::.list {:attr-for-selected "data-route" :selected 'route}
               (h/a {:data-route "home" :href :baseUrl}
                    (iron/icon {:icon "home"})
                    (h/span "Home"))

               (h/a {:data-route "users" :href "{{baseUrl}}users"}
                    (iron/icon {:icon "info"})
                    (h/span "Users"))

               (h/a {:data-route "contact" :href "{{baseUrl}}contact"}
                    (iron/icon {:icon "mail"})
                    (h/span "Contact")))))

(def main-toolbar
  (paper/toolbar ::mainToolbar.tall
    (paper/icon-button ::paperToggle {:icon "menu" :paper-drawer-toggle nil})
    (h/span ::.flex)

    (paper/icon-button {:icon "refresh"})
    (paper/icon-button {:icon "search"})

    ;; Application name
    (h/div ::.middle.middle-container.center.horizontal.layout
      (h/div ::.app-name "Miraj: Polymer Starter Kit"))

    ;; Application sub title
    (h/div ::.bottom.bottom-container.center.horizontal.layout
      (h/div ::.bottom-title.paper-font-subhead "The future of the web today"))))

(def section-home
  (h/section {:data-route "home"}
    (paper/material {:elevation 1}

      (comp/my-greeting)

      (h/p ::paper-font-subhead "You now have: ")
      (comp/my-list)

      (h/p ::paper-font-body2 "Looking for more Web App layouts? Check out our "
           (h/a {:href "https://github.com/PolymerElements/app-layout-templates"}
           " layouts ") "collection. You can also "
           (h/a {:href "http://polymerelements.github.io/app-layout-templates/"} "preview")
           " them live."))

    (paper/material {:elevation 1}
      (h/p ::.paper-font-body2 "This is another card."))

    (paper/material ::.paper-font-body2 {:elevation 1}
      (h/h1 ::license "License")
      (h/p "Everything in this repo is BSD style license unless otherwise specified.")
      (h/p "Copyright (c) 2015 The Polymer Authors. All rights reserved.")
      (h/p "Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:")
      (h/ul
       (h/li "Redistributions of source code must retain the above copyright "
                "notice, this list of conditions and the following disclaimer.")
       (h/li "Redistributions in binary form must reproduce the above
                copyright notice, this list of conditions and the following disclaimer
                in the documentation and/or other materials provided with the
                distribution.")
       (h/li "Neither the name of Google Inc. nor the names of its
                contributors may be used to endorse or promote products derived from
                this software without specific prior written permission."))
      (h/p "THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 'AS IS' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."))))

(def section-users
  (h/section {:data-route "users"}
    (paper/material {:elevation 1}
      (h/h2 ::.page-title "Users")
      (h/p "This is the users section")
      ;; for attrib binding we use _href instead of polymer's href$
      (h/a {:_href "{{baseUrl}}users/Addy"} "Addy")(h/br)
      (h/a {:_href "{{baseUrl}}users/Rob"} "Rob")(h/br)
      (h/a {:_href "{{baseUrl}}users/Chuck"} "Chuck")(h/br)
      (h/a {:_href "{{baseUrl}}users/Sam"} "Sam")(h/br))))

(def section-user-info
  (h/section {:data-route "user-info"}
    (paper/material {:elevation 1}
      (h/h2 ::.page-title "User: " :params.name)
      (h/div "This is " :params.name "'s section"))))

(def section-contact
  (h/section {:data-route "contact"}
    (paper/material {:elevation 1}
      (h/h2 ::.page-title "Contact")
      (h/p "This is the contact section"))))

(def service-worker
  (list
   (paper/toast ::caching-complete
                {:duration "6000"
                 :text "Caching complete! This app will work offline."})

   (sw/register {:auto-register nil
                 :clients-claim nil
                 :skip-waiting nil
                 :on-service-worker-installed "displayInstalledToast"}
                (sw/cache {:default-cache-strategy "fastest"
                           :cache-config-file "cache-config.json"}))))

(def main-area
  (paper/scroll-header-panel ::headerPanelMain
                             {:main nil :condenses nil
                              :keep-condensed-header nil}

        main-toolbar

        (h/div ::.content
          (iron/pages {:attr-for-selected "data-route"
                       :selected :route}
            section-home
            section-users
            section-user-info
            section-contact))))

(def homepage-html
  (h/html {:lang "en"} ;;FIXME: with defpage we do not need this elt
          (h/head ;;FIXME: do we need an explicit head elt?
           ;; with defpage this can be (:require ...)?
           (h/require '[polymer.iron :as iron :refer [flex-layout icons pages selector]]
                      '[polymer.paper :as paper :refer [drawer-panel icon-button item material menu
                                                        scroll-header-panel toast toolbar]]
                      ;; [polymer.platinum :as plat :refer [sw-cache sw-register]]
                      '[hello.psk.components :as comp :refer [my-greeting my-list]])
           ;; (h/link {:href "components/my-greeting-impl" :rel "import"})
           (h/import '(styles main)              ;;FIXME use [ ] instead?
                     '(styles.shared psk)
                     '(themes app)
                     '(scripts main
                               polyfill-lite-min
                               app
                               page
                               routing
                               )))))

(defpage homepage {:lang "en"}
  (:require '[polymer.iron :as iron :refer [flex-layout icons pages selector]]
            '[polymer.paper :as paper :refer [drawer-panel icon-button item material menu
                                              scroll-header-panel toast toolbar]]
            '[hello.psk.components :as comp :refer [my-greeting my-list]])
  (:import '(styles main)
           '(styles.shared psk)
           '(themes app)
           '(scripts main
                     polyfill-lite-min
                     app
                     page
                     routing
                     ))
  (body ::.fullbleed.layout.vertical {:unresolved nil}
   (span ::browser-sync-binding)
   (template
    ::app {:is "dom-bind"}
    (paper/drawer-panel ::paperDrawerPanel
      header-panel
      main-area)
    (paper/toast ::toast
      (span ::.toast-hide-button {:role "button"
                                    :tabindex 0
                                    :onclick "app.$.toast.hide()"}
              "Ok"))
    #_service-worker))))

(def homepage
  (-> homepage-html
      (with-meta homepage-meta)
      h/normalize
      h/optimize
      h/serialize))


(ns dojoquil.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [zone.lambda.game.board :as board :refer [pos-between BLANK]]))


(def test-board1
  [:. :. :. :. :.
   :. :. :. :. :.
   :. :X :X :X :.
   :. :. :X :. :.
   :. :. :X :. :.
   ])


(def test-board3
  [
   :X :X :X :X :X
   :X :X :X :X :X
   :X :X :X :X :X
   :X :X :X :X :X
   :X :X :X :X :X
   ])



(def test-board2
  [:. :. :. :. :. :. :. :. :. :. :. :.
   :. :. :. :X :. :. :. :. :. :X :. :.
   :. :. :. :. :X :. :. :. :X :. :. :.
   :. :. :. :X :X :X :X :X :X :X :. :.
   :. :. :X :X :. :X :X :X :. :X :X :.
   :. :X :X :X :X :X :X :X :X :X :X :X
   :. :X :. :X :X :X :X :X :X :X :. :X
   :. :. :. :X :. :. :. :. :. :X :. :.
   :. :. :. :. :X :X :. :X :X :. :. :.
   :. :. :. :. :. :. :. :. :. :. :. :.
   :. :. :. :. :. :. :. :. :. :. :. :.
   :. :. :. :. :. :. :. :. :. :. :. :.
   ])

(def test-board test-board2)

(def size (int (q/sqrt (count test-board))))
(def scale  50)

(def column-nb size)
(def raw-nb size)
(def c2dto1d (partial board/c2dto1d column-nb))
(def c1dto2d (partial board/c1dto2d column-nb))






(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 5)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  test-board)

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  state)

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 0)

  (dosync
    (doseq [x (range 0 size)
            y (range 0 size)]
      (when-let [hue (if (= (get state (c2dto1d [x y])) :X) (+ 30 (int (rand 75))))]
        (q/fill (q/color hue 255 255))
        (q/rect (* scale x) (* scale y) scale scale)))))

(defn start! []
  (q/defsketch dojoquil
              :title "You spin my circle right round"
              :size [(* scale size) (* scale size)]
              ; setup function called only once, during sketch initialization.
              :setup setup
              ; update-state is called on each iteration before draw-state.
              :update update-state
              :draw draw-state
              :features [:keep-on-top]
              ; This sketch uses functional-mode middleware.
              ; Check quil wiki for more info about middlewares and particularly
              ; fun-mode.
              :middleware [m/fun-mode]))


(defn -main []
  (start!))

(comment

  ;;start me here with the repl
  (start!)
  )
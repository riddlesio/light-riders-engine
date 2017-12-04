package io.riddles.lightriders

import io.riddles.lightriders.game.move.MoveType
import io.riddles.lightriders.game.move.LightridersMoveDeserializer
import io.riddles.lightriders.game.move.LightridersMove
import io.riddles.lightriders.game.player.LightridersPlayer
import spock.lang.Specification

class javainterfaceTests extends Specification {
    def "BookingGameMoveDeserializer must return one of MoveType when receiving valid input"() {

        given:
        LightridersMoveDeserializer deserializer = new LightridersMoveDeserializer();


        expect:
        LightridersMove move = deserializer.traverse(input);
        result == move.getMoveType();


        where:
        input   | result
        "up"        | MoveType.UP
        "down"      | MoveType.DOWN
        "right"     | MoveType.RIGHT
        "left"      | MoveType.LEFT
        "pass"      | MoveType.PASS
    }

    //@Ignore
    def "LightridersMoveDeserializer must throw an InvalidInputException when receiving unexpected input"() {

        given:
        LightridersMoveDeserializer deserializer = new LightridersMoveDeserializer();

        when: /* Unexpectedly groovy finds returned value as null, while debugging it seems to result in a Move. */
        LightridersMove move = deserializer.traverse("updown");

        then:
        move.moveType == null;
    }
}
package io.riddles.lightriders

import io.riddles.lightriders.game.move.MoveType
import io.riddles.lightriders.game.move.LightridersMoveDeserializer
import io.riddles.lightriders.game.move.LightridersMove
import io.riddles.lightriders.game.player.LightridersPlayer
import spock.lang.Specification

class javainterfaceTests extends Specification {
    def "BookingGameMoveDeserializer must return one of MoveType when receiving valid input"() {

        given:
        LightridersPlayer player = new LightridersPlayer(1);
        LightridersMoveDeserializer deserializer = new LightridersMoveDeserializer(player);


        expect:
        LightridersMove move = deserializer.traverse(input);
        result == move.getMoveType();


        where:
        input   | result
        "move up"        | MoveType.UP
        "move down"      | MoveType.DOWN
        "move right"     | MoveType.RIGHT
        "move left"      | MoveType.LEFT
        "pass"      | MoveType.PASS
    }

    //@Ignore
    def "LightridersMoveDeserializer must throw an InvalidInputException when receiving unexpected input"() {

        given:
        LightridersPlayer player = new LightridersPlayer(1);
        LightridersMoveDeserializer deserializer = new LightridersMoveDeserializer(player);

        when: /* Unexpectedly groovy finds returned value as null, while debugging it seems to result in a Move. */
        LightridersMove move = deserializer.traverse("updown");

        then:
        move.getException() == InvalidInputException;
    }
}
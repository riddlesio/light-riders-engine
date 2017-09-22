/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package io.riddles.lightriders

import io.riddles.javainterface.exception.InvalidInputException
import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.state.AbstractState
import io.riddles.javainterface.io.FileIOHandler
import io.riddles.lightriders.engine.LightridersEngine
import io.riddles.javainterface.io.IOHandler
import io.riddles.lightriders.game.move.LightridersMove
import io.riddles.lightriders.game.move.LightridersMoveDeserializer
import io.riddles.lightriders.game.move.MoveType
import io.riddles.lightriders.game.player.LightridersPlayer
import spock.lang.Specification
import spock.lang.Ignore

/**
 * io.riddles.lightriders.engine.LightridersEngineSpec - Created on 8-6-16
 *
 * [description]
 *
 * @author joost
 */

class LightridersEngineSpec extends Specification {

    public static class TestEngine extends LightridersEngine {

        TestEngine(PlayerProvider<LightridersEngine> playerProvider, String wrapperInput) {
            super(playerProvider, null);
            this.ioHandler = new FileIOHandler(wrapperInput);
        }
    }


    //@Ignore
    def "test running of standard game"() {
        println("test running of standard game")

        setup:
        String[] botInputs = new String[4]

        def wrapperInput = "./src/test/resources/wrapper_input.txt"
        botInputs[0] = "./src/test/resources/bot1_input.txt"
        botInputs[1] = "./src/test/resources/bot2_input.txt"
        botInputs[2] = "./src/test/resources/bot1_input.txt"
        botInputs[3] = "./src/test/resources/bot2_input.txt"
        PlayerProvider<LightridersPlayer> playerProvider = new PlayerProvider<>();
        LightridersPlayer player1 = new LightridersPlayer(0);
        player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        LightridersPlayer player2 = new LightridersPlayer(1);
        player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);


        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState initialState = engine.willRun()
        AbstractState finalState = engine.run(initialState);
        engine.didRun(initialState, finalState);

        expect:
        engine.configuration.getInt("maxRounds") == -1
    }


    def "LightridersMoveDeserializer must return one of MoveType when receiving valid input"() {

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

}
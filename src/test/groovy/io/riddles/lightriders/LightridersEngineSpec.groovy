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

import io.riddles.javainterface.game.player.PlayerProvider
import io.riddles.javainterface.game.state.AbstractState
import io.riddles.javainterface.io.FileIOHandler
import io.riddles.lightriders.engine.LightridersEngine
import io.riddles.javainterface.io.IOHandler
import io.riddles.lightriders.game.LightridersSerializer
import io.riddles.lightriders.game.board.LightridersBoard
import io.riddles.lightriders.game.player.LightridersPlayer
import io.riddles.lightriders.game.processor.LightridersProcessor
import io.riddles.lightriders.game.state.LightridersState
import javafx.scene.effect.Light
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

    class TestEngine extends LightridersEngine {

        TestEngine(PlayerProvider<LightridersEngine> playerProvider, String wrapperInput) {
            super(playerProvider, null);
            this.ioHandler = new FileIOHandler(wrapperInput);
        }
    }



    @Ignore
    def "test engine setup 2 players"() {

        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"

        PlayerProvider<LightridersPlayer> playerProvider = new PlayerProvider<>();
        LightridersPlayer player1 = new LightridersPlayer(1); player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        LightridersPlayer player2 = new LightridersPlayer(2); player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState state = engine.willRun()
        state = engine.run(state);
        /* Fast forward to final state */
        while (state.hasNextState()) state = state.getNextState();

        //state.getBoard().dumpBoard();
        LightridersProcessor processor = engine.getProcessor();

        expect:
        state instanceof LightridersState;
        state.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,1,.,.,.,.,2,2,2,2,.,.,.,.,.,.,.,1,.,.,.,.,2,.,.,.,.,.,.,.,.,.,.,1,.,.,1,1,2,.,.,.,.,.,.,.,.,.,.,1,1,1,1,.,2,.,.,.,.,.,.,.,.,.,.,.,.,.,.,2,2,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.";
        processor.getWinnerId(state) == 2;
    }




    @Ignore
    def "test engine setup 4 players"() {

        setup:
        String[] botInputs = new String[4]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"
        botInputs[2] = "./test/resources/bot3_input.txt"
        botInputs[3] = "./test/resources/bot2_input.txt"

        PlayerProvider<LightridersPlayer> playerProvider = new PlayerProvider<>();
        LightridersPlayer player1 = new LightridersPlayer(0); player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        LightridersPlayer player2 = new LightridersPlayer(1); player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);
        LightridersPlayer player3 = new LightridersPlayer(8); player3.setIoHandler(new FileIOHandler(botInputs[2])); playerProvider.add(player3);
        LightridersPlayer player4 = new LightridersPlayer(9); player4.setIoHandler(new FileIOHandler(botInputs[3])); playerProvider.add(player4);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState state = engine.willRun()
        state = engine.run(state);
        /* Fast forward to final state */
        while (state.hasNextState()) state = state.getNextState();

        //state.getBoard().dumpBoard();
        LightridersProcessor processor = engine.getProcessor();

        expect:
        state instanceof LightridersState;
        state.getBoard().toString() == ".,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,8,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,8,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,8,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,8,.,.,0,.,.,.,.,1,1,1,1,.,.,.,.,8,.,.,0,.,.,.,.,1,.,.,.,.,.,.,.,8,.,.,0,.,.,0,0,1,.,.,.,.,.,.,.,8,.,.,0,0,0,0,.,1,.,.,.,.,.,.,.,8,1,1,1,1,1,1,1,1,.,.,.,.,.,.,.,8,8,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,8,8,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,8,8,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,8,.,.,.,.,9,9,9,9,.,.,.,.,.,.,.,.,.,.,.,.,9,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,9,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,9,.,.,.,.,.,.";
        processor.getWinnerId(state) == 8;
    }



    @Ignore
    def "test LightridersSerializer functions"() {
        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"

        PlayerProvider<LightridersPlayer> playerProvider = new PlayerProvider<>();
        LightridersPlayer player1 = new LightridersPlayer(0); player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        LightridersPlayer player2 = new LightridersPlayer(1); player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState state = engine.willRun()
        state = engine.run(state);

        /* Fast forward to final state */
        LightridersSerializer serializer = new LightridersSerializer(playerProvider);
        String output = serializer.traverseToString(engine.getProcessor(), state);
        System.out.println(output);
        expect:
        output == "{\"settings\":{\"players\":{\"names\":[\"player0\",\"player1\"],\"count\":2}},\"score\":9,\"winner\":1,\"states\":[{\"round\":0,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"4,5\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"12,4\",\"id\":1}]},{\"round\":0,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"4,5\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"11,4\",\"id\":1}]},{\"round\":1,\"players\":[{\"isCrashed\":false,\"hasError\":true,\"position\":\"4,6\",\"id\":0,\"error\":\"Invalid input: Move isn't valid\"},{\"isCrashed\":false,\"hasError\":false,\"position\":\"11,4\",\"id\":1}]},{\"round\":1,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"4,6\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"10,4\",\"id\":1}]},{\"round\":2,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"4,7\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"10,4\",\"id\":1}]},{\"round\":2,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"4,7\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,4\",\"id\":1}]},{\"round\":3,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"5,7\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,4\",\"id\":1}]},{\"round\":3,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"5,7\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,5\",\"id\":1}]},{\"round\":4,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"6,7\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,5\",\"id\":1}]},{\"round\":4,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"6,7\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,6\",\"id\":1}]},{\"round\":5,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"7,7\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,6\",\"id\":1}]},{\"round\":5,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"7,7\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,7\",\"id\":1}]},{\"round\":6,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"7,6\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,7\",\"id\":1}]},{\"round\":6,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"7,6\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,8\",\"id\":1}]},{\"round\":7,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"8,6\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"9,8\",\"id\":1}]},{\"round\":7,\"players\":[{\"isCrashed\":false,\"hasError\":false,\"position\":\"8,6\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"8,8\",\"id\":1}]},{\"round\":8,\"players\":[{\"isCrashed\":true,\"hasError\":false,\"position\":\"8,6\",\"id\":0},{\"isCrashed\":false,\"hasError\":false,\"position\":\"8,8\",\"id\":1}]}]}";
    }

    //@Ignore
    def "test LightridersSerializer JSON Output"() {
        setup:
        String[] botInputs = new String[2]
        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"

        PlayerProvider<LightridersPlayer> playerProvider = new PlayerProvider<>();
        LightridersPlayer player1 = new LightridersPlayer(0); player1.setIoHandler(new FileIOHandler(botInputs[0])); playerProvider.add(player1);
        LightridersPlayer player2 = new LightridersPlayer(1); player2.setIoHandler(new FileIOHandler(botInputs[1])); playerProvider.add(player2);

        def engine = new TestEngine(playerProvider, wrapperInput)

        AbstractState state = engine.willRun()
        state = engine.run(state);
        state = engine.didRun(state);

        /* Fast forward to final state */
        expect:
        1 * engine.didRun(state)
    }
}
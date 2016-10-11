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

import io.riddles.lightriders.engine.LightridersEngine
import io.riddles.javainterface.io.IOHandler
import io.riddles.lightriders.game.state.LightridersState
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
        String finalBoard;

        TestEngine(IOHandler ioHandler) {
            super();
            this.ioHandler = ioHandler;
        }

        TestEngine(String wrapperFile, String[] botFiles) {
            super(wrapperFile, botFiles)
        }


        IOHandler getIOHandler() {
            return this.ioHandler;
        }

        void setup() {
            super.setup();
        }

//        @Override
//        protected void finish(LightridersState initialState) {
//            this.finalBoard = initialState.getBoard().toRepresentationString(players)
//            super.finish(initialState);
//        }
    }


    class StandardTestEngine extends LightridersEngine {

        StandardTestEngine(IOHandler ioHandler) {
            super();
            this.ioHandler = ioHandler;
        }

        StandardTestEngine(String wrapperFile, String[] botFiles) {
            super(wrapperFile, botFiles)
        }

        IOHandler getIOHandler() {
            return this.ioHandler;
        }

        void setup() {
            super.setup();
        }

        void finish() {
            super.finish();
        }
    }

    def engine = new TestEngine(Mock(IOHandler));



    @Ignore
    def "test engine setup"() {
        println("test engine setup")

        setup:
        engine.getIOHandler().getNextMessage() >>> ["initialize", "bot_ids 1,2", "start"]

        when:
        engine.setup()

        then:
        1 * engine.getIOHandler().sendMessage("ok")

        expect:
        engine.getPlayers().size() == 2
        engine.getPlayers().get(0).getId() == 1
        engine.getPlayers().get(1).getId() == 2
    }




    //@Ignore
    def "test running of standard game"() {
        println("test running of standard game")

        setup:
        String[] botInputs = new String[4]

        def wrapperInput = "./test/resources/wrapper_input.txt"
        botInputs[0] = "./test/resources/bot1_input.txt"
        botInputs[1] = "./test/resources/bot2_input.txt"
        botInputs[2] = "./test/resources/bot1_input.txt"
        botInputs[3] = "./test/resources/bot2_input.txt"
        def engine = new StandardTestEngine(wrapperInput, botInputs)

        engine.run()

        expect:
        engine.configuration.getInt("maxRounds") == 40
    }

}
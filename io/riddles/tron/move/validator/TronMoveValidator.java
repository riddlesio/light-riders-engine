package io.riddles.tron.move.validator;

import io.riddles.boardgame.model.Board;
import io.riddles.boardgame.model.Move;
import io.riddles.game.move.MoveValidator;
import io.riddles.tron.TronMove;

import java.util.ArrayList;

/**
 * ${PACKAGE_NAME}
 *
 * This file is a part of chess
 *
 * Copyright 2016 - present Riddles.io
 * For license information see the LICENSE file in the project root
 *
 * @author Niko
 */
public final class TronMoveValidator implements MoveValidator {

	private ArrayList<MoveValidator> validators;

	public TronMoveValidator() {

		ArrayList<MoveValidator> validators = new ArrayList<>();

		validators.add(new PassValidator());
		validators.add(new DirectionValidator());
		
		this.validators = validators;
	}

	@Override
	public Boolean isApplicable(Move move, Board board) {
		return true;
	}

	@Override
	public Boolean isValid(Move move, Board board) {

		for (MoveValidator validator : validators) {

			if (validator.isApplicable(move, board)) {
				if (!validator.isValid(move, board)) {
					return false;
				}
			}
		}

		return true;
	}

}
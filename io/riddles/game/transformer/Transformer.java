package io.riddles.game.transformer;

/**
 * Created by Niko on 29/05/16.
 */
public interface Transformer<SourceType, TargetType> {

    TargetType transform(SourceType state);
}

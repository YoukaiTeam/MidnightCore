package net.absolutioncraft.commons.bukkit.rank.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.absolutioncraft.api.shared.http.exception.HTTPException;
import net.absolutioncraft.commons.bukkit.rank.changeable.ChangedRank;

/**
 * @author MelonDev
 * @since 1.0.0
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public final class RankChangeResponse {
    private Response response;
    private ChangedRank changedRank;

    private HTTPException exception;

    public static RankChangeResponse getSuccessResponse(ChangedRank changedRank) {
        return new RankChangeResponse(Response.SUCCESS, changedRank, null);
    }

    public enum Response {
        INTERNAL_ERROR, SENT_THROUGH_REDIS, SUCCESS
    }
}

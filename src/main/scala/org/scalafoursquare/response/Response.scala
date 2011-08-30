package org.scalafoursquare.response

// Venue Details

case class VenueContact(phone: Option[String], formattedPhone: Option[String], twitter: Option[String])
case class VenueLocation(address: Option[String], crossStreet: Option[String],  city: Option[String],
                         state: Option[String], postalCode: Option[String], country: Option[String],
                         lat: Option[Double], lng: Option[Double], distance: Option[Int])
case class VenueStats(checkinsCount: Int, usersCount: Int, tipCount: Int)

case class VenueHereNowGroup(`type`: String, name: String, count: Int, items: List[CheckinForVenue])
case class VenueHereNow(count: Int, groups: List[VenueHereNowGroup])
case class VenueMayor(count: Int, user: Option[UserCompact])

case class VenueTipGroup(`type`: String, name: String, count: Int, items: List[TipForList])
case class VenueTips(count: Int, groups: List[VenueTipGroup])
case class VenueBeenHere(count: Int)

case class VenuePhotoGroup(`type`: String, name: String, count: Int, items: List[PhotoForList])
case class VenuePhotos(count: Int, groups: List[VenuePhotoGroup])
case class VenueTodos(count: Int, items: List[TodoForVenue])
case class VenueHereNowCompact(count: Int)

case class VenueLists(count: Int, items: List[String])

trait VenueKernel {
  def id: String
  def name: String
  def itemId: String
  def contact: VenueContact
  def location: VenueLocation
  def categories: List[VenueCategoryCompact]
  def verified: Boolean
  def stats: VenueStats
  def url: Option[String]
}

case class VenueCore(id: String, name: String, itemId: String, contact: VenueContact, location: VenueLocation,
                     categories: List[VenueCategoryCompact], verified: Boolean, stats: VenueStats, url: Option[String])

case class VenueCompact(id: String, name: String, itemId: String, contact: VenueContact, location: VenueLocation,
                        categories: List[VenueCategoryCompact], verified: Boolean, stats: VenueStats, url: Option[String],
                        specials: Option[List[Special]], hereNow: Option[VenueHereNowCompact],
                        events: Option[List[CompactEvent]]) extends VenueKernel

case class VenueDetailExtended(
  createdAt: Long,
  hereNow: VenueHereNow,
  mayor: VenueMayor,
  tips: VenueTips,
  tags: List[String],
  specials: List[Special],
  specialsNearby: List[Special],
  shortUrl: String,
  timeZone: String,
  beenHere: Option[VenueBeenHere],
  photos: Option[VenuePhotos],
  description: Option[String],
  events: Option[List[CompactEvent]],
  lists: Option[VenueLists],
  todos: Option[VenueTodos]
)

case class VenueDetail(
  core: VenueCore,
  extended: VenueDetailExtended
) {
  def id = core.id
  def name = core.name
  def itemId = core.itemId
  def contact = core.contact
  def location = core.location
  def categories = core.categories
  def verified = core.verified
  def stats = core.stats
  def url = core.url

  def createdAt = extended.createdAt
  def hereNow = extended.hereNow
  def mayor = extended.mayor
  def tips = extended.tips
  def tags = extended.tags
  def specials = extended.specials
  def specialsNearby = extended.specialsNearby
  def shortUrl = extended.shortUrl
  def timeZone = extended.timeZone
  def beenHere = extended.beenHere
  def photos = extended.photos
  def description = extended.description
  def events = extended.events
  def lists = extended.lists
  def todos = extended.todos

}

case class VenueDetailResponse(venue: VenueDetail)

case class VenueTrendingResponse(venues: List[VenueCompact])
case class VenueSearchResponse(venues: List[VenueCompact])

case class VenueAddResponse(venue: VenueDetail)

case class VenueHereNowList(count: Int, items: List[CheckinForVenue])
case class VenueHereNowResponse(hereNow: VenueHereNowList)

case class VenueTipsList(count: Int, items: List[TipForList])
case class VenueTipsResponse(tips: VenueTipsList)

case class VenuePhotosList(count: Int, items: List[PhotoForList])
case class VenuePhotosResponse(photos: VenuePhotosList)

case class VenueLinkProvider(id: String)
case class VenueLinkItem(provider: VenueLinkProvider, linkedId: String, url: Option[String])
case class VenueLinksList(count: Int, items: List[VenueLinkItem])
case class VenueLinksResponse(links: VenueLinksList)

case class VenueMarkTodoResponse(todo: TodoForVenue)

case class VenueFlagResponse()
case class VenueEditResponse()
case class VenueProposeEditResponse()

case class VenueExploreReason(`type`: String, message: String)
case class VenueExploreRecommendationReasonList(count: Int, items: List[VenueExploreReason])
case class VenueExploreRecommendation(reasons: VenueExploreRecommendationReasonList,
                                      venue: VenueCompact,
                                      todos: Option[List[TodoForVenue]],
                                      tips: Option[List[TipForList]])
case class VenueExploreGroup(`type`: String, name: String, count: Option[Int], items: List[VenueExploreRecommendation])
case class VenueExploreKeyword(displayName: String, keyword: String)
case class VenueExploreWarning(text: String)
case class VenueExploreKeywordList(count: Int, items:List[VenueExploreKeyword])
case class VenueExploreResponse(keywords: VenueExploreKeywordList,
                                warning: Option[VenueExploreWarning],
                                groups: List[VenueExploreGroup])

// User Details
// TODO: find which of these can be merged; which items are optional
case class UserContact(phone: Option[String], email: Option[String], twitter: Option[String], facebook: Option[String])
case class UserBadgesCount(count: Int)
case class UserMayorshipsSummary(count: Int, items: List[VenueCompact]) // items sometimes returned as empty list here

case class UserCheckinsSummary(count: Int, items: List[CheckinForFriend]) // This is only ever the most recent checkin

case class FriendGroupCompact(`type`: String, name: String, count: Int, items: List[UserCompact]) // mutual friends vs. other friends for self/friends/following/others
case class FriendsOthersCompactView(count: Int, groups: List[FriendGroupCompact])

case class UserFollowersCount(count: Int)
case class UserFollowingCount(count: Int)
case class UserRequestsCount(count: Int)
case class UserTipsCount(count: Int)
case class UserTodosCount(count: Int)
case class UserScores(recent: Int, max: Int, goal: Option[Int], checkinsCount: Int)
case class UserDetail(id: String, firstName: String, lastName: Option[String], photo: String,
                      gender: String, homeCity: String, relationship: Option[String],
                      `type`: String, pings: Option[Boolean], contact: UserContact,
                      badges: UserBadgesCount, mayorships: UserMayorshipsSummary,
                      checkins: UserCheckinsSummary, friends: FriendsOthersCompactView,
                      followers: Option[UserFollowersCount], following: Option[UserFollowingCount],
                      requests: Option[UserRequestsCount],
                      tips: UserTipsCount, todos: UserTodosCount, scores: UserScores) extends UserKernel
case class UserDetailResponse(user: UserDetail)

case class UserCompact(id: String, firstName: String, lastName: Option[String], photo: String,
                          gender: String, homeCity: String, relationship: Option[String]) extends UserKernel

case class UserCore(id: String, firstName: String, lastName: Option[String], photo: String,
                          gender: String, homeCity: String, relationship: Option[String],
                          `type`: Option[String]) extends UserKernel

case class UserRequestResponse(requests: List[UserCompact])

// TODO: not my favorite solution for polymorphic return data.  Will think on this.
trait Primitive {}
case class IntPrimitive(v: Int) extends Primitive
case class DoublePrimitive(v: Double) extends Primitive
case class StringPrimitive(v: String) extends Primitive
case class BooleanPrimitive(v: Boolean) extends Primitive
case object NothingPrimitive extends Primitive

class UserSearchUnmatched(val map: Map[String, List[Primitive]])
case class UserSearchResponse(results: List[UserCompact], unmatched: UserSearchUnmatched)

trait UserKernel {
  def id: String
  def firstName: String
  def lastName: Option[String]
  def photo: String
  def gender: String
  def homeCity: String
  def relationship: Option[String]
}

// Venue Categories

trait VenueCategoryKernel {
  def id: Option[String]
  def name: String
  def pluralName: String
  def icon: String
}
case class VenueCategoryCore(id: Option[String], name: String, pluralName: String, icon: String) extends VenueCategoryKernel
case class VenueCategoryCompact(id: Option[String], name: String, pluralName: String, icon: String, parents: List[String], primary: Option[Boolean]) extends VenueCategoryKernel
case class VenueCategoryWithChildren(id: Option[String], name: String, pluralName: String, icon: String, categories: List[VenueCategoryWithChildren]) extends VenueCategoryKernel

case class VenueCategoriesResponse(categories: List[VenueCategoryWithChildren])

case class UserPhotoUpdateResponse(user: UserDetail)

case class UserMayorshipsList(count: Int, items: List[VenueCompact])
case class UserMayorshipsResponse(mayorships: UserMayorshipsList)

case class UserTipsList(count: Int, items: List[TipForList])
case class UserTipsResponse(tips: UserTipsList)

case class UserCheckins(count: Int, items: List[CheckinForFriend])
case class UserCheckinsResponse(checkins: UserCheckins)

case class UserVenueHistoryElement(beenHere: Int, venue: VenueCompact)
case class UserVenueHistoryList(count: Int, items: List[UserVenueHistoryElement])
case class UserVenueHistoryResponse(venues: UserVenueHistoryList)

case class AnnotatedEntity(indices: List[Int], `type`: String)
case class MentionEntity(indices: List[Int], `type`: String, user: UserCompact)

case class CheckinLocation(name: String, lat: Double, lng: Double)

case class CheckinCore(id: String,
                       createdAt: Long,
                       `type`: String,
                       `private`: Option[Boolean],
                       shout: Option[String],
                       isMayor: Option[Boolean],
                       timeZone: String)

case class CheckinForFriend(id: String,
                            createdAt: Long,
                            `type`: String,
                            `private`: Option[Boolean],
                            shout: Option[String],
                            isMayor: Option[Boolean],
                            timeZone: String,
                            entities: Option[List[MentionEntity]],
                            venue: Option[VenueCompact],
                            location: Option[CheckinLocation],
                            event: Option[CompactEvent],
                            photos: Option[PhotoList],
                            comments: Option[CommentList],
                            source: Option[OAuthSource])

case class CheckinForVenue(id: String,
                           createdAt: Long,
                           `type`: String,
                           `private`: Option[Boolean],
                           shout: Option[String],
                           isMayor: Option[Boolean],
                           timeZone: String,
                           user: Option[UserCompact])

case class CheckinForFeed(id: String,
                          createdAt: Long,
                          `type`: String,
                          `private`: Option[Boolean],
                          shout: Option[String],
                          isMayor: Option[Boolean],
                          timeZone: String,
                          venue: Option[VenueCompact],
                          location: Option[CheckinLocation],
                          user: Option[UserCompact])

case class CheckinOverlapList(count: Int, items: List[CheckinForVenue])
case class CheckinDetail(id: String,
                         createdAt: Long,
                         `type`: String,
                         `private`: Option[Boolean],
                         shout: Option[String],
                         isMayor: Option[Boolean],
                         timeZone: String,
                         entities: Option[List[MentionEntity]],
                         user: Option[UserCompact],
                         venue: Option[VenueCompact],
                         location: Option[CheckinLocation],
                         source: Option[OAuthSource],
                         distance: Option[Int],
                         photos: Option[PhotoList],
                         comments: Option[CommentList],
                         event: Option[CompactEvent],
                         overlaps: Option[CheckinOverlapList])

case class CheckinDetailResponse(checkin: CheckinDetail)

case class RecentCheckinsResponse(recent: List[CheckinDetail])

case class CheckinDeleteCommentResponse(checkin: CheckinDetail)
case class CheckinAddCommentResponse(comment: Comment)

case class Comment(id: String, createdAt: Long, user: UserCompact, text: String, entities: Option[List[MentionEntity]])
case class CommentList(count: Int, items: List[Comment])

case class CompactEvent(id: String, name: Option[String])

case class AddCheckinResponse(checkin: CheckinForFriend)

// User Updates:
case class UserSetPingsResponse(user: UserDetail)
case class UserUnfriendResponse(user: UserDetail)
case class UserFriendRequestResponse(user: UserDetail)
case class UserApproveFriendResponse(user: UserDetail)
case class UserDenyFriendshipResponse(user: UserDetail)

case class UserFriendsList(count: Int, summary: Option[String], items: List[UserCompact])
case class UserFriendsResponse(friends: UserFriendsList)

case class AllSettings(receivePings: Boolean,
                       receiveCommentPings: Boolean,
                       twitter: Option[String],
                       sendToTwitter: Boolean,
                       sendMayorshipsToTwitter: Boolean,
                       sendBadgesToTwitter: Boolean,
                       facebook: Option[Long],
                       sendToFacebook: Boolean,
                       sendMayorshipsToFacebook: Boolean,
                       sendBadgesToFacebook: Boolean,
                       foreignConsent: String)
case class AllSettingsResponse(settings: AllSettings)

case class LeaderboardItem(user: UserCompact, scores: UserScores, rank: Int)
case class Leaderboard(count: Int, items: List[LeaderboardItem])
case class LeaderboardResponse(leaderboard: Leaderboard)

case class Image(prefix: Option[String], sizes: Option[List[Int]], name: Option[String], fullPath: Option[String])

case class BadgeGroup(`type`: String, name: String, image: Image, items: List[String], groups: List[BadgeGroup])
case class BadgeSets(groups: List[BadgeGroup])
case class BadgeUnlocks(checkins: List[CheckinForFriend])
case class Badge(id: String, badgeId: String, name: String, description: Option[String], hint: Option[String],
                 image: Image , unlocks:List[BadgeUnlocks])
case class Badges(map: Map[String, Badge])
case class UserBadgesResponse(sets: BadgeSets, badges: Badges, defaultSetType: String)

// Tips
case class TodoStat(count: Int)
case class DoneStat(count: Int)

trait TipKernel {
  def id: String
  def createdAt: Long
  def itemId: String
  def text: String
  def url: Option[String]
  def status: Option[String]
  def photo: Option[PhotoCore]
  def photourl: Option[String]
}

trait TipStats {
  def todo: TodoStat
  def done: DoneStat
}

case class TipCore(id: String, createdAt: Long, itemId: String, text: String, url: Option[String], status: Option[String],
               photo: Option[PhotoCore], photourl: Option[String]) extends TipKernel
case class TipForList(id: String, createdAt: Long, itemId: String, text: String, url: Option[String], status: Option[String],
               photo: Option[PhotoCore], photourl: Option[String], todo: TodoStat, done: DoneStat,
               venue: Option[VenueCompact], user: Option[UserCompact]) extends TipKernel with TipStats
case class TipSearchResponse(tips: List[TipForList])

case class TodoDetailGroup(`type`: String, name: String, count: Option[Int], items: List[UserCompact])
case class DoneDetailGroup(`type`: String, name: String, count: Option[Int], items: List[UserCompact])

case class TodoDetail(count: Int, groups: List[TodoDetailGroup])
case class DoneDetail(count: Int, groups: List[DoneDetailGroup])

case class TipDetail(id: String, createdAt: Long, itemId: String, text: String, url: Option[String], status: Option[String],
               photo: Option[PhotoCore], photourl: Option[String], venue: Option[VenueCompact],
               user: Option[UserCompact], todo: TodoDetail, done: DoneDetail) extends TipKernel
case class TipDetailResponse(tip: TipDetail)

case class AddTipResponse(tip: TipForList)
case class TipMarkTodoResponse(todo: TodoForList)

case class TipUnmarkResponse(tip: TipDetail)
case class TipMarkDoneResponse(tip: TipDetail)

case class TodoListName(name: String)
case class TodoForList(id: String, createdAt: Long, list: Option[TodoListName], tip: Option[TipForList])
case class TodoForVenue(id: String, createdAt: Long, list: Option[TodoListName], tip: Option[TipForList])

case class UserTodosList(count: Int, items: List[TodoForList])
case class UserTodosResponse(todos: UserTodosList)

// Photos

case class PhotoList(count: Int, items: List[PhotoForList])
case class PhotoDimension(url: String, width: Int, height: Int)
case class PhotoDimensionList(count: Int, items: List[PhotoDimension])
case class OAuthSource(name: String, url: String)
trait PhotoKernel {
  def id: String
  def createdAt: Long
  def url: String
  def sizes: PhotoDimensionList
  def source: Option[OAuthSource]
}
case class PhotoCore(id: String, createdAt: Long, url: String, sizes: PhotoDimensionList, source: Option[OAuthSource])
case class PhotoForList(id: String, createdAt: Long, url: String, sizes: PhotoDimensionList, source: Option[OAuthSource],
                        user: Option[UserCompact], visibility: String, checkin: Option[CheckinForFriend]) extends PhotoKernel

case class PhotoDetail(id: String, createdAt: Long, url: String, sizes: PhotoDimensionList, source: Option[OAuthSource],
                        user: Option[UserCompact], venue: Option[VenueCompact],
                        checkin: Option[CheckinCore], tip: Option[TipForList]) extends PhotoKernel


case class AddPhotoResponse(photo: PhotoCore)
case class PhotoDetailResponse(photo: PhotoDetail)


// Specials
case class SpecialRedemptionInteraction(prompt: String, waitingPrompt: Option[String], timeoutPrompt: Option[String],
                                        timeoutExplanation: Option[String], input: String, entryUrl: Option[String],
                                        purchaseUrl: Option[String], exitUrl: Option[String])

case class Special(id: String, `type`: String, message: String, description: Option[String],
                   finePrint: Option[String], unlocked: Option[Boolean],
                   icon: String, title: String, state: Option[String], progress: Option[Int], progressDescription:
                   Option[String], detail: Option[String], target: Option[Int], friendsHere: Option[List[UserCompact]],
                   provider: String, iconUrl: Option[String], redemption: String,
                   interaction: Option[SpecialRedemptionInteraction], venue: Option[VenueCore])

case class SpecialDetailResponse(special: Special)

case class SpecialList(count: Int, items: List[Special])
case class SpecialsSearchResponse(specials: SpecialList)

case class FlagSpecialResponse()

// Notifications
case class UpdateDetailResponse(notification: UpdateDetail)

case class NotificationList(count: Int, items: List[UpdateDetail])
case class NotificationsResponse(notifications: NotificationList)
case class MarkNotificationsReadResponse()


// Settings
case class SettingsDetailResponse(value: Primitive)
case class ChangeSettingsResponse(settings: AllSettings)

case class Meta(code: Int, errorType: Option[String], errorDetail: Option[String])

case class Notification(`type`: String /*, item: DIFFERENT TYPES */)

case class Response[T](meta: Meta, notifications: Option[List[Notification]], response: Option[T])

case class MultiResponse[A,B,C,D,E](meta: Meta, notifications: Option[List[Notification]], responses: (Option[Response[A]], Option[Response[B]], Option[Response[C]], Option[Response[D]], Option[Response[E]]))
case class MultiResponseList[A](meta: Meta, notifications: Option[List[Notification]], responses: Option[List[Response[A]]])

case class Url(url: String)

trait UpdateTarget {}
case class UserUpdateTarget(v: UserCompact) extends UpdateTarget
case class CheckinUpdateTarget(v: CheckinForFeed) extends UpdateTarget
case class VenueUpdateTarget(v: VenueCompact) extends UpdateTarget
case class ListUpdateTarget(/*v : List*/) extends UpdateTarget
case class TipUpdateTarget(v: TipForList) extends UpdateTarget
case class BadgeUpdateTarget(v: Badge) extends UpdateTarget
case class SpecialUpdateTarget(v: Special) extends UpdateTarget
case class UrlUpdateTarget(v: Url) extends UpdateTarget
case object NothingUpdateTarget extends UpdateTarget

case class UpdateDetail(ids: List[String], createdAt: Long, unread: Boolean,
                        image: Image, imageType: String, icon: Option[Image],
                        target: UpdateTarget, text: String, entities: List[AnnotatedEntity])




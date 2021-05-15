import { createFeatureSelector, createSelector } from '@ngrx/store';
import {userNode} from '../redusers/userReducer';
import {DefaultUserStore} from '../../../components/models/InitialUserState';

export const selectUserFeatures = createFeatureSelector<DefaultUserStore>(userNode);

export const selectUsers = createSelector(selectUserFeatures, state => state.users);
export const selectPrincipal = createSelector(selectUserFeatures, state => state.principal);
export const selectCart = createSelector(selectUserFeatures, state => state.cart);
export const selectPurchases = createSelector(selectUserFeatures, state => state.purchases);
export const selectCommentsUser = createSelector(selectUserFeatures, state => state.comments);
export const selectUserAvatar = createSelector(selectUserFeatures, state => state.avatar);
export const selectAllAvatars = createSelector(selectUserFeatures, data => data.avatars);

import { ActionReducerMap, MetaReducer } from '@ngrx/store';
import {userNode, userReducer} from './userReducer';
import {environment} from '../../../../environments/environment';
import {pizzaNode, pizzaReducer} from './pizzaReducer';

export interface State {
  [userNode]: {};
  [pizzaNode]: {};
}

export const reducers: ActionReducerMap<State> = {
  [userNode]: userReducer,
  [pizzaNode]: pizzaReducer,
};
export const metaReducers: MetaReducer<State>[] = !environment.production ? [] : [];

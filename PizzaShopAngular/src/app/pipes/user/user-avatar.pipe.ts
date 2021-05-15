import { Pipe, PipeTransform } from '@angular/core';
import {Avatar} from '../../components/models/Avatar';

@Pipe({
  name: 'userAvatar'
})
export class UserAvatarPipe implements PipeTransform {

  transform(value: string, args: Avatar[]): string {
    if (args){
      const avatar = args.find(value1 => value1.path === value);
      if (avatar){
        return avatar.path;
      }
     else {
       return null;
      }
    }
  }

}

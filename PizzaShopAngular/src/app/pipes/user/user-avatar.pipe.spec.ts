import { UserAvatarPipe } from './user-avatar.pipe';

describe('UserAvatarPipe', () => {
  it('create an instance', () => {
    const pipe = new UserAvatarPipe();
    expect(pipe).toBeTruthy();
  });
});

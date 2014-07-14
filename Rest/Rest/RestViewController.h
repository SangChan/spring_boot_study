//
//  RestViewController.h
//  Rest
//
//  Created by SangChan on 2014. 7. 14..
//  Copyright (c) 2014년 Spring. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RestViewController : UIViewController

@property (nonatomic, strong) IBOutlet UILabel *greetingId;
@property (nonatomic, strong) IBOutlet UILabel *greetingContent;

- (IBAction)fetchGreeting;


@end
